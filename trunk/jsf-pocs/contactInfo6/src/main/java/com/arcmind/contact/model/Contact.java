package com.arcmind.contact.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@SuppressWarnings("serial")
public class Contact implements Serializable, Validateable {
	private String firstName;
	private String lastName;
	private String email;
	protected long id;
	private Group group;
	private List<Tag> tags;
	private PhoneNumber workPhoneNumber;
	private PhoneNumber homePhoneNumber;
	private PhoneNumber mobilePhoneNumber;
	private String description;
	private boolean active;
	private ContactType type = ContactType.PERSONAL;
	private String zip;
	private short age;
	private Date birthDate;
	

	public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Contact(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Contact() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
	    this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Contact other = (Contact) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Contact: %s %s", firstName, lastName);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Tag[] getTags() {
	    if (tags != null) {
	        return tags.toArray(new Tag[tags.size()]);
	    } else {
	        return null;
	    }
	}

	public void setTags(Tag[] tags) {
		this.tags = Arrays.asList(tags);
	}
	
	public String getTagNames() {
	    if (tags.size()<1) {
	        return "";
	    }
	    StringBuilder builder = new StringBuilder();
	    for (Tag tag : tags) {
	        builder.append(tag.getName() + ", ");
	    }
	    return builder.toString().substring(0, builder.length()-2);
	}

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void validate() throws ValidationException {
        if (
             homePhoneNumber == null  &&
             workPhoneNumber == null  &&
             mobilePhoneNumber == null             
           ) {
            throw new ValidationException("At least one phone number " +
            		"must be set", "");
            
        }
    }

    public PhoneNumber getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(PhoneNumber workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public PhoneNumber getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(PhoneNumber homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public PhoneNumber getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(PhoneNumber mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

}