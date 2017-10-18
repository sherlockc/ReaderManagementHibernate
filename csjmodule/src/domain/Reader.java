package domain;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_reader")
public class Reader {
    @Id
    private String id;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String description;

    public Reader(){
    }

    public Reader(Reader reader)
    {
        this.id = reader.getId();
        this.name = reader.getName();
        this.gender = reader.getGender();
        this.phone = reader.getPhone();
        this.email = reader.getEmail();
        this.description = reader.getDescription();
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
