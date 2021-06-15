---
# try also 'default' to start simple
theme: unicorn
# apply any windi css classes to the current slide
class: 'text-center'
download: true
# https://sli.dev/custom/highlighters.html
highlighter: shiki
# some information about the slides, markdown enabled
info: |
  ## Slide for 3d-projection.

---

# 삼차원 투영

2-9 23. 이현우, 2-12 21. 이정원.

<div class="pt-12">
  <span @click="$slidev.nav.next" class="px-2 p-1 rounded cursor-pointer" hover="bg-white bg-opacity-10">
    발표 시작 <carbon:arrow-right class="inline"/>
  </span>
</div>

<a href="https://github.com/lhwdev/project-3d-projection" target="_blank" alt="GitHub"
  class="abs-br m-6 text-xl icon-btn opacity-50 !border-none !hover:text-white">
  <carbon-logo-github />
</a>

<!--
발표 시작!
-->

---
logoHeader: 'https://www.dawntraoz.com/images/logo.svg'
layout: intro
introImage: 'public/3d-projection-perspective.png'
---

# 3차원 투영

3차원 공간의 점들을 2차원 상으로 옮기는 작업




<!--
You can have `style` tag in markdown to override the style for the current page.
Learn more: https://sli.dev/guide/syntax#embedded-styles
-->

<!-- <style>
h1 {
  background-color: #2B90B6;
  background-image: linear-gradient(45deg, #4EC5D4 10%, #146b8c 20%);
  background-size: 100%;
  -webkit-background-clip: text;
  -moz-background-clip: text;
  -webkit-text-fill-color: transparent;
  -moz-text-fill-color: transparent;
}
</style> -->

---

# 사원수 (Quaternion)


### 정의

$$
q = a + bi + cj + dk \ (a, b, c, d \in \Bbb{R})
$$

---

# 사원수 (Quaternion)

### 특성

- 곱셈의 교환법칙이 성립하지 않음.
  $$
  ij = k \\
  ji = -k
  $$


---
layout: intro
introImage: 'public/quaternion-rotation.jpg'
---

# 사원수를 통한 회전의 계산

- 사원수는 '짐벌 락'이라 불리는 현상이 일어나지 않음
- 두 상태 사이에서 보간하기 쉬워 애니매이션 등에서도 잘 쓰임


$$
\bold{q'}=\bold{p}\bold{q}\bold{p}
$$

<br>
https://eater.net/quaternions/video/intro


---

# 회전 구현하기

- 현재 구현체에서는 원근감과 물체의 원근을 고려하지 않음



---

# 회전 구현하기
https://lhwdev.github.io/3d-projection/preview.html


---

# 부족한 점
- 실제 이러한 계산을 하는데에는 homogeneous coordinates(동차좌표)라는 것이 사용됨.
  사원수로 원근감을 구현하기에는 어려움.

---
class: text-center
---

# 끝

<br>

감사합니다.

<a href="https://github.com/lhwdev/project-3d-projection" target="_blank" alt="GitHub"
  class="abs-br m-6 text-xl icon-btn opacity-50 !border-none !hover:text-white">
  <carbon-logo-github />
</a>

