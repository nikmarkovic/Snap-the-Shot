package rs.ac.metropolitan.cs330.znamenitosti.model.dto;

/**
 *
 * @author nikola
 */
public class CityDto {

    private Long id;
    private String name;

    public CityDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
