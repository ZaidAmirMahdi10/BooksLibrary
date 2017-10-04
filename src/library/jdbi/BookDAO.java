package library.jdbi;
import io.dropwizard.hibernate.AbstractDAO;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

import library.core.Book;

import java.util.List;

/**
 * 
 * @author Seva Meyer
 * DAO class connects to DB
 * and performs calls to it
 *
 */
public class BookDAO extends AbstractDAO<Book> {

    @Inject
    public BookDAO(SessionFactory factory) {
        super(factory);
    }

    
    /**
     * find a book by a given id
     * @param id
     * @return a book
     */
    public Optional<Book> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    /**
     * creates a new book entity
     * @param book
     * @return
     */
    public Book create(Book book) {
        return persist(book);
    }

    /**
     * lists all books available 
     * @return a list
     */
    public List<Book> findAll() {
    	Query q = namedQuery("library.core.Book.findAll");
    	/*q.setFirstResult((page-1)*limit);
    	q.setMaxResults(limit); //page*limit
*/    	return list(q);
    }

    /**
     * update a book 
     * @param book
     * @return
     */
    public Book update(Book book){

        return  persist(book);
    }

    /**
     * delete a book
     * @param book
     */
    public void delete(Book book){
        currentSession().delete(book);
    }

    
    /**
     * Search method, provides both a search by author 
     * and title and pagination
     * @param search - the string from search box
     * @param page - a number of the page
     * @param limit - number of rows to display
     * @return - a list with found books by search
     */
    public List<Book> searchBook(String search){
    	Query q = namedQuery("library.core.Book.findBySearch");
    	q.setString("search","%"+search+"%");
    	/*q.setFirstResult((page-1)*limit);
    	q.setMaxResults(limit); //page*limit
*/    	return list(q);
    }
    
    
}