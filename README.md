# SpringMVC_Practice_ISC
토큰을 사용하는 분리된 인증 서버와 로직 서버 사이의 통신 구현

# 서버 구조
![제목 없음](https://user-images.githubusercontent.com/45464572/227788122-ffc6ed21-cff1-43ff-ab28-59bb88f5c99c.png)


* 인증 서버
  * 외부와의 통신을 위해 8080 포트 개방
  * 인증 외의 작업은 로직 서버에 위임


* 로직 서버
  * 회원가입
  * 로그인, jwt 생성

# 서버간 통신
1. 클라이언트로부터의 요청을 HttpServletRequest 객체로 받는다.
2. HttpServletRequest로부터 클라이언트 요청 정보를 얻는다.
3. HttpUrlConnection을 통해 클라이언트 요청 정보를 로직 서버에 전달한다.
4. 로직 서버로부터 받은 응답을 HttpServletResponse 객체를 통해 클라이언트에게 전달한다.
