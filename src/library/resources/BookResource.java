package library.resources;

import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Optional;

import library.core.Book;
import library.jdbi.BookDAO;

/**
 * 
 * @author Seva Meyer
 * 
 * resource class (or a controller) maps client calls
 * to backend and sends back JSON objects 
 *
 */
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource { //@TODO Response response = Response.status(Status.BAD_REQUEST).entity("Must provide a valid sensor Id!").build();
	  //@TODO throw new WebApplicationException(response);
	
	private final BookDAO dao; 
	
	public BookResource (BookDAO dao){
		this.dao = dao;
	}


	/**
	 * 
	 * @return a list of all boos from db
	 */

    @GET
    @UnitOfWork
    public List<Book> listBooks(@QueryParam("search") String search
    							/*@QueryParam("page") int page,
    							@QueryParam("limit") int limit*/){
    	if(search!=null){
    		return dao.searchBook(search);
    	}
        return dao.findAll();

    }
    
    
    /**
     * 
     * @param bookId
     * @return a book retrieved by the given id
     */
    @GET
    @Path("/{bookId}")
    @UnitOfWork
    public Book getBook(@PathParam("bookId") long bookId){
    	Book book = findSafely(bookId);
    	if(book==null){
    		throw new WebApplicationException(Response.Status.NOT_FOUND);
    	}
    	return book;
    }
    
    /**
     * 
     * @param book to be added to the db
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Status addBook(@Valid Book book){
    	dao.create(book);
    	return Response.Status.CREATED;
    }
  
    
    
    /**
     * 
     * @param book that will be updated
     */
    @PUT
    @Path("/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Status updateBook(@Valid Book book){
    	System.out.println(book.getId());
    	dao.update(book);
    	return Response.Status.OK;
    	
    }
    

    /**
     * deletes a book from db
     * @param bookId book id to be deleted
     */
    @DELETE
    @Path("/{bookId}")
    @UnitOfWork
    public Status deleteBook(@PathParam("bookId") long bookId){
    	dao.delete(this.getBook(bookId));
		return Response.Status.NO_CONTENT;
    }
    
    
    /**
     * a method to retrieve a bok from the db
     * @param bookId 
     * @return the book with the given id
     */
	private Book findSafely(long bookId) {
        final Optional<Book> book = dao.findById(bookId);
        if (!book.isPresent()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return book.get();
    }

}