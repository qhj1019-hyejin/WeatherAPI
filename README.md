# WeatherAPI

IntelliJ , Java

### Notes while working

자바는 소스코드를 실행 할 때, 소스코드 파일명과 동일한 bulbic 클래스를 컴파일 해서 동일한 클래스 내에 main 메소드를 실행해야 한다.

IOException : 
파일이 존재하지 않거나 접근할 수 없는 경우 등의 입출력 관련 오류 시 발생

StringBuilder : 변경 가능한 문자열을 만들어준다.
String을 합치는 작업 시 사용하기 좋다.
기존의 데이터를 더하는 방식으로 속도가 빠르며 부하가 적다.

String일 경우 객체 변경 불가능
-> 다른 문자열로 연결 시 새 문자열 생성 후 이전 문자열은 가비지 컬렉터로 들어감

hasNext() : boolean 타입으로 반환 된다.
-> 값이 있으면 True 없으면 False

next() : 매개변수 혹은 iterator 되는 타입 
-> 아무 타입으로 반환 가능 ( Iterator에 입력된 값들이 String일 경우 String 값 반환)

scanner.close(); 를 해줘야 하는 이유 :
- 파일을 열어놓고 닫지않을 경우 파일 손상 가능
- os자원에서 프로세스 표준 입력을 가지고 와서 돌려줘야 함
파일 또는 네트워크 등 리소스일 경우 close 하기 전 다른 프로그램에서 액세스를 못함

toString() - 객체를 문자열로 리턴하는 메소드

JSONParser() - json데이터를 파싱하여 객체 또는 배열로 변환 시키기 위해 사용 된다.

JSONObject() - key,vlaue로 데이터를 표현하는 객체

parse() - JSON 문자열을 JsonObject 또는  JsonArray로 변환

jsonObject.get("current"); - current라는 키로 값 추출

-------------

### GitHub push process

[과정 및 원인 해결]<br/>

IntelliJ에서 GitHub 연결을 하고 push를 했을 경우 Branch가 기본값인 main이 아닌 master로 push가 되어서,
main으로 branch를 변경하려고 했으나 계속해서 자료를 찾아가며 작업을 했지만 원인도 정확히 모르겠고, main으로 push가 실패였다.

결국 IntelliJ 터미널로 기존에 되어있던 Github연결을 끊고 다시 시작하는 마음으로 연동을 진행 해보았다.

1. git init
2. git add 특정 파일 경로 또는 . (전체)
3. git remote add origin 깃허브 주소

4. git checkout -b main 
브랜치 전환 하려했으나 fatal: a branch named 'main' already exists
이미 존재하는 브랜치라고 나와서 

5. git branch로 브랜치 확인 시 main 브랜치가 정상적으로 나왔다.
6. git pull origin main
7. git push origin main
-> fatal: refusing to merge unrelated histories

push가 안되는 원인은 Readme.md 파일을 만들어서 그렇다고 한다.
그동안 정상적으로 main에 push가 안되었던 이유가 이러했던 것 같다.

8. git pull origin main --allow-unrelated-histories
접근을 강제로 허락 해주는 것

이 과정에서 오류가 나지 않았기 때문에 다시 git push origin main 을 했고,
git push에 성공했다.

