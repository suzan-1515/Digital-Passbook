package np.com.krishna.sahakaari.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileResponse implements Serializable {

    @SerializedName("user")
    private User user;
    @SerializedName("father_name")
    private String fatherName;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("address")
    private String address;
    @SerializedName("contact")
    private String contact;
    @SerializedName("dob")
    private String dob;
    @SerializedName("gender")
    private String gender;
    @SerializedName("citizenship_number")
    private String citizenshipNumber;
    @SerializedName("photo")
    private String photo;
    @SerializedName("amount")
    private float amount;
    @SerializedName("account_number")
    private String accountNumber;
    @SerializedName("membership_number")
    private String membershipNumber;
    @SerializedName("descriptions")
    private String description;
    @SerializedName("type_of_plan")
    private String type;
    @SerializedName("timestamp")
    private String createdDate;
    @SerializedName("duration")
    private String duration;
    @SerializedName("payment_date")
    private String paymentDate;
    @SerializedName("assigned_staff")
    private User fieldAgent;

    public ProfileResponse(User user, String fatherName, String fullName, String address,
                           String contact, String dob, String gender, String citizenshipNumber,
                           String photo, float amount, String accountNumber, String membershipNumber,
                           String description, String type, String createdDate, String duration,
                           String paymentDate, User fieldAgent) {
        this.user = user;
        this.fatherName = fatherName;
        this.fullName = fullName;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
        this.citizenshipNumber = citizenshipNumber;
        this.photo = photo;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.membershipNumber = membershipNumber;
        this.description = description;
        this.type = type;
        this.createdDate = createdDate;
        this.duration = duration;
        this.paymentDate = paymentDate;
        this.fieldAgent = fieldAgent;
    }

    public ProfileResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public User getFieldAgent() {
        return fieldAgent;
    }

    public void setFieldAgent(User fieldAgent) {
        this.fieldAgent = fieldAgent;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenshipNumber() {
        return citizenshipNumber;
    }

    public void setCitizenshipNumber(String citizenshipNumber) {
        this.citizenshipNumber = citizenshipNumber;
    }

    @Override
    public String toString() {
        return "ProfileResponse{" +
                "user=" + user +
                ", fatherName='" + fatherName + '\'' +
                ", address='" + address + '\'' +
                ", photo='" + photo + '\'' +
                ", amount=" + amount +
                ", accountNumber='" + accountNumber + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", duration='" + duration + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", fieldAgent=" + fieldAgent +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }
}
