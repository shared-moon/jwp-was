# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 기능구현목록

### Step1

[x] 요청 Parsing 유틸 작성

[x] GET 요청 파싱

[x] POST 요청 파싱

[x] Query String 파싱

[x] enum 적용

### Step2

[x] webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

[x] /user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.

[x] form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

[ ] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다

[ ] 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.

[ ] “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다

[ ] Stylesheet 파일을 지원하도록 구현하도록 한다.