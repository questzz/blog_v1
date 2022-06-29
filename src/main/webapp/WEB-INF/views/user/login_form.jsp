<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ include file="../layout/header.jsp" %>

<div class="container">
	<!-- loginProc 를 만들지 않음(스프링 시큐리티가 가로채서 진행) -->
	<form  action="/auth/loginProc" method="post">
	  <div class="form-group">
	    <label for="username">username:</label>
	    <input type="text" class="form-control" name="username"  placeholder="Enter username" id="username" />
	  </div>
	  <div class="form-group">
	    <label for="password">Password:</label>
	    <input type="password" class="form-control" name="password" placeholder="Enter password" id="password" />
	  </div>
	  <div class="form-group form-check">
	    <label class="form-check-label">
	    <input class="form-check-input" type="checkbox" /> Remember me </label>
	  </div>
	  <button type="submit" id="btn-login"  class="btn btn-primary">로그인</button>
	  <a href="https://kauth.kakao.com/oauth/authorize?client_id=141b8184eb9a5187f47d408f956643c0&redirect_uri=http://localhost:9090/auth/kakao/callback&response_type=code">
	   	<img src="/image/kakao_login.png" width="74" height="38">
	  </a>
	</form>
	
</div>
<br/>
<!-- 
<script src="/js/user.js"></script>
 -->
<%@ include file="../layout/footer.jsp" %>
