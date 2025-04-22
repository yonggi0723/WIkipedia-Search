# Wikipedia Search App (Android 기술과제)

위키피디아 API를 활용하여 검색어의 요약 정보 및 관련 미디어 리스트를 조회하고 표시하는 Android 프로젝트입니다.  
Compose를 사용하지 않고, `View` + `ListView` 기반으로 구현하였습니다.

---

## ✨ 주요 기능

- 검색어 입력 시 요약 정보 + 관련 미디어 리스트 표시
- Swipe to Refresh 기능으로 미디어 리스트 재요청
- 리스트 아이템 클릭 시 캡션의 키워드를 이용한 재검색
- 커스텀 툴바, 헤더, 푸터 구성
- WebView를 통해 요약 페이지 보기
- 이미지 비동기 로딩 및 비트맵 처리
- MVVM + Clean Architecture 구조

---

## 🧩 기술 스택

- **언어**: Kotlin  
- **아키텍처**: MVVM + UseCase 기반 클린 아키텍처  
- **UI**: XML + ListView + DataBinding  
- **비동기 처리**: Coroutine  
- **데이터 계층**:  
  - Repository / DataSource 패턴  
  - Bitmap 처리 UseCase 분리  
- **테스트**:  
  - ViewModel 단위 테스트  
  - Coverage 50% 이상

---

## 📄 기술 문서

해당 프로젝트는 NHN 실전 기술과제를 기반으로 진행되었으며,  
요구사항 분석, 커스텀 HTTP 모듈 설계, 테스트 전략, 회고까지 포함된 기술 문서를 제공합니다.

👉 [기술문서 PDF 바로 보기](./docs/WikipediaSearch_TechDoc_KimYonggi.pdf)

---

## 📁 프로젝트 구조

---

## 🎨 디자인 고려 사항

- 클린 아키텍처를 최대한 간결하게 반영하여 단방향 흐름 유지
- 의존성 주입은 간단한 수동 DI 방식으로 구성
- 이미지 로딩은 외부 라이브러리 없이 `BitmapFactory`로 처리
- 디스크 캐싱 및 메모리 LRU 캐싱은 추후 확장 가능하도록 분리 설계

---

## 🔍 예시 화면

> 기술과제 제출 목적이므로, UI는 간결한 구현 위주로 구성되었습니다.

<img width="781" alt="스크린샷 2025-04-22 오후 5 13 45" src="https://github.com/user-attachments/assets/ace85e42-effe-4a19-94f2-2ac40d76924a" />

---

## 🧪 테스트 및 커버리지

- ViewModel 단위 테스트 포함  
- 핵심 UseCase 로직 테스트 포함  
- **현재 커버리지**: 약 50%

---

## 📌 기타

- 외부 이미지 로딩 라이브러리 사용 금지 조건에 따라  
  `Bitmap`을 직접 다운로드하여 처리
- 추후 `Compose` 기반 UI로 확장 가능하도록 일부 모듈 설계
