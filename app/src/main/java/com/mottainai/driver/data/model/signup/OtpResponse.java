package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponse {

@SerializedName("status")
@Expose
private boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("user_id")
@Expose
private String userId;

public boolean getStatus() {
return status;
}

public void setStatus(boolean status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

}