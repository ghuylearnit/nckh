const listButton = document.querySelectorAll(".inner-menu li a");
listButton.forEach(button => {
  button.addEventListener("click", () => {
    const buttonActiveCurrent = document.querySelector(".inner-menu a.active");
    if(buttonActiveCurrent) {
      buttonActiveCurrent.classList.remove("active");
    }
    button.classList.add("active");
  });
});

// Set active class based on current page
const currentPath = window.location.pathname.split('/').pop();
listButton.forEach(button => {
  if (button.getAttribute('href') === currentPath) {
    button.classList.add('active');
  }
});

