# 씨드데이
![seeday_logo](/screenshots/logo.jpg)
## 소개
3가지 기록을 통해 꾸준함과 습관을 만들어 보세요!
운동, 습관, 하루 기록 등을 한 곳에서 관리하고, 알림을 통해 꾸준히 실천할 수 있도록 돕는 것을 목표로 합니다.

- 메인 홈에서 오늘의 기록과 상태를 한눈에 확인
- 운동, 습관, 목표 등 카테고리별 기록 관리
- 알림/푸시를 통한 리마인드
- 온보딩 및 로그인 플로우 제공

## 개발환경
- Language
  - Kotlin
- Android
  - `minSdk = 28`
  - `targetSdk = 35`
  - `compileSdk = 35`

## 모듈 구조

본 프로젝트는 **multi-module** 구조로 되어 있으며, 핵심 기능과 화면을 모듈 단위로 분리하여 관리합니다.

- **📦 build-logic**
  - `convention` Gradle 플러그인 및 공통 설정을 관리하는 모듈

- **📦 app**
  - 실제 안드로이드 애플리케이션 모듈
  - `applicationId = "see.day.app"`

- **📦 core**
  - `core:data`
  - `core:domain`
  - `core:model`
  - `core:designsystem`
  - `core:ui`
  - `core:navigation`
  - `core:messaging`

- **📦 feature**
  - `feature:main`
  - `feature:login`
  - `feature:onboarding`
  - `feature:home`
  - `feature:daily`
  - `feature:exercise`
  - `feature:habit`
  - `feature:setting`
  - `feature:notification`
  - `feature:goal`

## 기술스택

- **UI**
  - Jetpack Compose
  - 모듈화된 UI 레이어(`core:designsystem`, `core:ui`)
  - Navigation 모듈(`core:navigation`) 기반 화면 전환

- **DI**
  - Hilt

- **네트워크**
  - Retrofit
  - OkHttp
    - Authenticator를 활용한 인증 관련 처리
    - Interceptor를 활용한 공통 헤더 추가, 로깅, 에러 처리 등

- **테스트**
  - Mock 기반 테스트 적용
    - 네트워크/레포지토리 등을 Mock으로 대체하여 도메인/뷰모델 단위 테스트 가능하도록 구성

- **Firebase**
  - Firebase BoM
  - Crashlytics
  - Analytics
  - Cloud Messaging(FCM)

- **인증**
  - Kakao SDK (`libs.kakao.v2.user`)

- **기타**
  - Kotlin Serialization
  - 멀티 모듈 기반 공통 Convention 플러그인(`build-logic`)

## 기술 정보

- **Multi-Module & Feature 단위 구성**
  - `core` / `feature` 레이어로 분리하여 관심사를 나눔
  - `build-logic` 모듈에서 공통 Gradle 설정을 관리해, 각 모듈에서 일관된 세팅을 유지
  - 모듈 단위 의존성 분리를 통해 빌드 시간 및 유지보수성 개선

- **클린 아키텍처 지향**
  - `core:domain`, `core:data`, `core:model`, `core:ui` 등으로 레이어를 분리하여 역할을 명확히 함
  - 도메인 로직과 UI, 데이터 레이어 간 결합도를 줄이는 방향으로 설계

- **Firebase Crashlytics & Analytics**
  - 릴리즈 빌드에서 Crashlytics 연동 (`mappingFileUploadEnabled = true`)
  - 앱 크래시 및 사용 로그를 기반으로 안정성 개선 및 사용자 행동 분석

- **FCM 기반 메시징**
  - `core:messaging` 모듈과 Firebase Cloud Messaging을 사용해 푸시 알림 기능 구성

- **카카오 인증/유저 API**
  - `libs.kakao.v2.user` 의존성을 통해 카카오 관련 기능(로그인/유저 정보 등)을 통합

- **서명 및 보안 설정**
  - `local.properties`를 통해 keystore 정보 관리
  - 서명 정보(keystore 파일 경로, 패스워드, alias 등)를 코드에 하드코딩하지 않고 외부 설정으로 분리

## 정보들
- **도메인/엔티티 설계**
  - `core:model` 모듈에 앱 전반에서 사용하는 공통 모델 정의
  - 기록/목표/습관 등 주요 도메인에 맞춘 엔티티 설계 예정

- **기능별 정리**
  - `feature:daily` : 일간 기록 관련 화면 및 로직
  - `feature:exercise` : 운동 기록 관련 기능
  - `feature:habit` : 습관 관리 기능
  - `feature:goal` : 목표 설정 및 관리
  - `feature:notification` : 알림/푸시 관련 화면 및 설정
  - `feature:setting` : 앱 설정 화면
