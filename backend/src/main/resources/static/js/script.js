// Afficher/masquer le mot de passe
document.addEventListener("DOMContentLoaded", function () {
    const toggle = document.getElementById("togglePassword");
    const input = document.getElementById("passwordInput");

    toggle.addEventListener("click", function () {
        const isPassword = input.getAttribute("type") === "password";
        input.setAttribute("type", isPassword ? "text" : "password");

        toggle.classList.toggle("bi-eye");
        toggle.classList.toggle("bi-eye-slash");
    });
});

const fileInput = document.getElementById('fileInput');
const textInput = document.querySelector('.file-upload-info');

fileInput.addEventListener('change', function () {
    textInput.value = this.files[0] ? this.files[0].name : '';
});


function togglePasswordSection() {
    const section = document.getElementById('passwordSection');
    const isVisible = section.style.display === 'block';

    if (isVisible) {
        section.style.display = 'none';
    } else {
        section.style.display = 'block';
        section.scrollIntoView({ behavior: 'smooth' });
    }
}

