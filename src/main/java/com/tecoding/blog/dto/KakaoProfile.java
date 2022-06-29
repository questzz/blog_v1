
package com.tecoding.blog.dto;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "connected_at", "properties", "kakao_account" })
public class KakaoProfile {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("connected_at")
	private String connectedAt;
	@JsonProperty("properties")
	private Properties properties;
	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	private void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	// Properties inner class
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "nickname", "profile_image", "thumbnail_image" })
	private class Properties {

		@JsonProperty("nickname")
		private String nickname;
		@JsonProperty("profile_image")
		private String profileImage;
		@JsonProperty("thumbnail_image")
		public String thumbnailImage;
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		@JsonAnySetter
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	} // end of Properties class

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "profile_nickname_needs_agreement", "profile_image_needs_agreement", "profile", "has_email",
			"email_needs_agreement", "is_email_valid", "is_email_verified", "email" })
	@Data
	public class KakaoAccount {

		@JsonProperty("profile_nickname_needs_agreement")
		private Boolean profileNicknameNeedsAgreement;
		@JsonProperty("profile_image_needs_agreement")
		private Boolean profileImageNeedsAgreement;
		@JsonProperty("profile")
		private Profile profile;
		@JsonProperty("has_email")
		private Boolean hasEmail;
		@JsonProperty("email_needs_agreement")
		private Boolean emailNeedsAgreement;
		@JsonProperty("is_email_valid")
		private Boolean isEmailValid;
		@JsonProperty("is_email_verified")
		private Boolean isEmailVerified;
		@JsonProperty("email")
		private String email;
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		@JsonAnySetter
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@JsonPropertyOrder({ "nickname", "thumbnail_image_url", "profile_image_url", "is_default_image" })
		@Generated("jsonschema2pojo")
		@Data
		public class Profile {

			@JsonProperty("nickname")
			private String nickname;
			@JsonProperty("thumbnail_image_url")
			private String thumbnailImageUrl;
			@JsonProperty("profile_image_url")
			private String profileImageUrl;
			@JsonProperty("is_default_image")
			private Boolean isDefaultImage;
			@JsonIgnore
			private Map<String, Object> additionalProperties = new HashMap<String, Object>();

			@JsonAnyGetter
			public Map<String, Object> getAdditionalProperties() {
				return this.additionalProperties;
			}

			@JsonAnySetter
			public void setAdditionalProperty(String name, Object value) {
				this.additionalProperties.put(name, value);
			}
		}
	} // end of kakaoAccount class

}
