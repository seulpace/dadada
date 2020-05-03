package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * * Lombok (롬복)
 *   vo클래스에 getter/setter, 생성자, toString 등등 반드시 필요한 메소드들을
 *   단지 어노테이션만으로 자동으로 만들어주는 라이브러리
 *   
 *   * 롬복 사용법
 *   1. lombok의 라이브러리 추가
 *   2. 라이브러리 다운만으로 끝이 아니라 설치까지 해야 됨
 *      해당 jar 파일 찾아서 더블클릭 후 설치
 *   3. sts 재부팅
 */
@Setter				// setter
@Getter				// getter
@NoArgsConstructor 	// 기본생성자
@AllArgsConstructor // 매개변수 생성자
@ToString			// toString 메소드
//@EqulsAndHashCode	// 
public class Member {
   
   private String userId;
   private String userPwd;
   private String userName;
   private String email;
   private String gender;
   private String age;
   private String phone;
   private String address;
   private Date enrollDate;
   private Date modifyDate;
   private String status;

   /*
   public Member() {
      
   }

   public Member(String userId, String userPwd, String userName, String email, String gender, String age, String phone,
         String address, Date enrollDate, Date modifyDate, String status) {
      super();
      this.userId = userId;
      this.userPwd = userPwd;
      this.userName = userName;
      this.email = email;
      this.gender = gender;
      this.age = age;
      this.phone = phone;
      this.address = address;
      this.enrollDate = enrollDate;
      this.modifyDate = modifyDate;
      this.status = status;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getUserPwd() {
      return userPwd;
   }

   public void setUserPwd(String userPwd) {
      this.userPwd = userPwd;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public Date getEnrollDate() {
      return enrollDate;
   }

   public void setEnrollDate(Date enrollDate) {
      this.enrollDate = enrollDate;
   }

   public Date getModifyDate() {
      return modifyDate;
   }

   public void setModifyDate(Date modifyDate) {
      this.modifyDate = modifyDate;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   @Override
   public String toString() {
      return "Member [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName + ", email=" + email
            + ", gender=" + gender + ", age=" + age + ", phone=" + phone + ", address=" + address + ", enrollDate="
            + enrollDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
   }
   
   */
}