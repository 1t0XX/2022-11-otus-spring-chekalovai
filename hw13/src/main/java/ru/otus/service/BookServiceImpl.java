package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Book;
import ru.otus.exception.*;
import ru.otus.repository.BookRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final MutableAclService aclService;

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        try {
            bookRepository.findAll().forEach(this::defaultCreatePermissionForAdult);
            return bookRepository.findAll();
        } catch (Exception e) {
            return Collections.emptyList();
            /*throw new FindAllBookException(e);*/
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new GetBookByIdException(id, new Throwable("Not Found Id Book")));
    }

    @Transactional
    @Override
    public Book saveBook(Book book) {
        try {
            Book resultBook = bookRepository.save(book);
            defaultCreatePermissionForAdult(resultBook);
            return resultBook;
        } catch (Exception e) {
            throw new SaveBookException(book, e);
        }
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteBookException(id, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByAuthor(Long id) {
        try {
            return bookRepository.findAllByAuthorId(id);
        } catch (Exception e) {
            throw new GetBookByAuthorException(id, e);
        }
    }

    private void defaultCreatePermissionForAdult(Book book) {
        final Sid sid = new GrantedAuthoritySid("ROLE_ADULT");
        ObjectIdentity oi = new ObjectIdentityImpl(book.getClass(), book.getId());
        MutableAcl acl;
        try {
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (Exception e) {
            acl = aclService.createAcl(oi);
        }
        Permission permission = BasePermission.READ;
        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        aclService.updateAcl(acl);
    }
}