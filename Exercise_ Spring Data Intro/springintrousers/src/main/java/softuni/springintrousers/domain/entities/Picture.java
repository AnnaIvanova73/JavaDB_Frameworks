package softuni.springintrousers.domain.entities;


import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {
    private String title;
    private String caption;
    private String path;
    private Album album;

    public Picture() {
    }

    @Column(name = "title",nullable = false,length = 30)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "caption")
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Column(name = "path",nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne(optional = false )
    @JoinColumn(name = "album",referencedColumnName = "id")
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
