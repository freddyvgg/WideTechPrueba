package co.widetech.widetechprueba.to;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	
	public static final String FNAME = "FNAME";
	public static final String LNAME = "LNAME";
	public static final String PHONE = "PHONE";
	public static final String EMAIL = "EMAIL";
	public static final String ID = "ID";
	
	
	/**
	 * GP: [6]
		0:  {
		Name: "FNAME"
		Value: "Jairo"
		}-
		1:  {
		Name: "LNAME"
		Value: "Moreno"
		}-
		2:  {
		Name: "PHONE"
		Value: "3016053124"
		}-
		3:  {
		Name: "EMAIL"
		Value: "jairomoreno1971@gmail.com"
		}-
		4:  {
		Name: "PASSWORD"
		Value: "3016053124"
		}-
		5:  {
		Name: "ID"
		Value: "151975"
		}
	 */
	
	private String id, fname, lname, phone, email;

	public User() {
		super();
	}

	public User(String id, String fname, String lname, String phone,
			String email) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.email = email;
	}
	
	
	public User(Parcel in)
	{
		this.id = in.readString();
		this.fname = in.readString();
		this.lname = in.readString();
		this.phone = in.readString();
		this.email = in.readString();
	}
	
	public User(Main main)
	{
		
		for(GP gp: main.getGP())
		{
			switch(gp.getName())
			{
				case ID:
					this.id = gp.getValue();
					break;
				case FNAME:
					this.fname = gp.getValue();
					break;
				case LNAME:
					this.lname = gp.getValue();
					break;
				case PHONE:
					this.phone = gp.getValue();
					break;
				case EMAIL:
					this.email = gp.getValue();
					break;
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname
				+ ", phone=" + phone + ", email=" + email + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(fname);
		dest.writeString(lname);
		dest.writeString(phone);
		dest.writeString(email);
		
	}

	public static final Parcelable.Creator<User> CREATOR =
	        new Parcelable.Creator<User>() {
	            public User createFromParcel(Parcel in) {
	                return new User(in);
	            }

	            public User[] newArray(int size) {
	                return new User[size];
	            }
	        };
}
