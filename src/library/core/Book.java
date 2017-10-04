package library.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.Override;

/**
 * @author Seva Meyer
 * Book entity class
 */

@Entity
@Table(name = "book")
@NamedQueries({
    @NamedQuery(
            name = "library.core.Book.findAll",
            query = "SELECT b FROM Book b"
    ),
    @NamedQuery(
    		name = "library.core.Book.findBySearch",
    		query = "SELECT b FROM Book b WHERE b.title LIKE :search OR b.author LIKE :search")
})
public class Book{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "author")
    @NotNull
    private String author;

    @Column(name = "date")
    private Long date;

    @Column(name = "description", length=2000)
    private String description;

    @Column(name = "image", length=1000)
    private String image;
    
    public Book(){}
    
    public Book(String title, String author){
    	this.title = title;
    	this.author = author;
    }

    public Book(String title, String author, String description, String image, Long date){
    	this.title = title;
    	this.author = author;
    	this.image = image;
    	this.description = description;
    	this.date = date;
    }
    @JsonProperty
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty
    public Long getId() {
        return id;
    }

   

    @JsonProperty
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(Long id) {

        this.id = id;
    }

}