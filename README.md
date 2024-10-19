백엔드 사용자 인증 <Spring Security + JWT RTR(feat. Redis)>

Architecture 구성 : Open JDK 21, Spring boot 3.2.1, Redis

서비스 설명 : 1. 서비스로 접근한 request에 대해 url에 따라 filter 단에서 구분하여 인증이 필요한 endpoint의 url의 요청시에 JWT AccessToken이 Header에 포함되어있다면 해당 Token의 
유효성을 검증(HMACSHA256 Decoding)
2. 유효성검증을 통과한 AccessToken의 payload의 정보를 이용하여 (userId, 회원코드 등) 해당 url의 endpoint의 로직을 처리
3. AccessToken이 존재하지 않는다면 Refresh Token이 쿠키에 포함되어있는지 확인 후 RefreshToken을 이용한 AccessToken 재발급등의 로직을 이용하여 토큰 재발급 및 요청 로직처리
4. RefreshToken도 존재하지 않는다면 로그인 페이지로 이동
5. 로그인시 AccessToken과 RefreshToken을 발급하여 Response를 전송 및 RefreshToken을 Redis에 저장 (key는 요청 IP등을 사용해 검증)
