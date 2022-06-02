<template>
  <footer class="footer">
    <div class="container-fluid">
      <ul class="nav">
        <li class="nav-item">
          <a
            href="https://github.com/HauHuynh1304/"
            target="_blank"
            rel="noopener"
            class="nav-link"
          >
            Human Resource Management Project
          </a>
        </li>
      </ul>
      <div class="copyright">
        <i class="fa fa-copyright" style="font-weight: 200;"></i>
        {{ year }}, made with <i class="tim-icons icon-heart-2"></i> by
        <a
          href="https://github.com/HauHuynh1304/"
          target="_blank"
          rel="noopener"
          >Hau Huynh</a
        >
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  data() {
    return {
      year: new Date().getFullYear(),
    };
  },
};
</script>
<style></style>
