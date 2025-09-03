document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const registerBtn = document.getElementById('register-btn');
    const loginBtn = document.getElementById('login-btn');
    const notification = document.getElementById('notification');
    const notificationText = document.getElementById('notification-text');

    registerBtn.addEventListener('click', function(e) {
        e.preventDefault();
        loginForm.style.display = 'none';
        registerForm.style.display = 'flex';
    });

    loginBtn.addEventListener('click', function(e) {
        e.preventDefault();
        registerForm.style.display = 'none';
        loginForm.style.display = 'flex';
    });

    // Validación del formulario de inicio de sesión
    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();
        let isValid = true;
        document.getElementById('username-error').textContent = '';
        document.getElementById('password-error').textContent = '';

        if (username === '') {
            document.getElementById('username-error').textContent = 'El usuario es obligatorio';
            isValid = false;
        } else if (username.length < 3) {
            document.getElementById('username-error').textContent = 'El usuario debe tener al menos 3 caracteres';
            isValid = false;
        }

        if (password === '') {
            document.getElementById('password-error').textContent = 'La contraseña es obligatoria';
            isValid = false;
        } else if (password.length < 6) {
            document.getElementById('password-error').textContent = 'La contraseña debe tener al menos 6 caracteres';
            isValid = false;
        }

        if (isValid) {
            const users = JSON.parse(localStorage.getItem('users')) || {};
            
            if (users[username] && users[username] === password) {
                showNotification('Inicio de sesión exitoso. Redirigiendo...', 'success');
                const remember = document.getElementById('remember').checked;
                if (remember) {
                    localStorage.setItem('currentUser', username);
                } else {
                    sessionStorage.setItem('currentUser', username);
                }

                setTimeout(() => {
                    window.location.href = 'MenuInicio.jsp'; 
                }, 2000);
            } else {
                showNotification('Usuario o contraseña incorrectos', 'error');
            }
        }
    });

    // Validación del formulario de registro
    registerForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const newUsername = document.getElementById('new-username').value.trim();
        const newPassword = document.getElementById('new-password').value.trim();
        const confirmPassword = document.getElementById('confirm-password').value.trim();
        let isValid = true;

        document.getElementById('new-username-error').textContent = '';
        document.getElementById('new-password-error').textContent = '';
        document.getElementById('confirm-password-error').textContent = '';

        if (newUsername === '') {
            document.getElementById('new-username-error').textContent = 'El usuario es obligatorio';
            isValid = false;
        } else if (newUsername.length < 3) {
            document.getElementById('new-username-error').textContent = 'El usuario debe tener al menos 3 caracteres';
            isValid = false;
        }

        if (newPassword === '') {
            document.getElementById('new-password-error').textContent = 'La contraseña es obligatoria';
            isValid = false;
        } else if (newPassword.length < 6) {
            document.getElementById('new-password-error').textContent = 'La contraseña debe tener al menos 6 caracteres';
            isValid = false;
        }

        if (confirmPassword === '') {
            document.getElementById('confirm-password-error').textContent = 'Debes confirmar tu contraseña';
            isValid = false;
        } else if (newPassword !== confirmPassword) {
            document.getElementById('confirm-password-error').textContent = 'Las contraseñas no coinciden';
            isValid = false;
        }

        if (isValid) {
            // Guardar usuario en localStorage
            const users = JSON.parse(localStorage.getItem('users')) || {};
            
            if (users[newUsername]) {
                showNotification('El usuario ya existe', 'error');
            } else {
                users[newUsername] = newPassword;
                localStorage.setItem('users', JSON.stringify(users));
                showNotification('Cuenta creada exitosamente. Ya puedes iniciar sesión.', 'success');
                
                // Cambiar al formulario de login después de 2 segundos
                setTimeout(() => {
                    registerForm.style.display = 'none';
                    loginForm.style.display = 'flex';
                    // Rellenar automáticamente el usuario
                    document.getElementById('username').value = newUsername;
                }, 2000);
            }
        }
    });

    // Función para mostrar notificaciones
    function showNotification(message, type) {
        notificationText.textContent = message;
        notification.className = 'notification show ' + type;
        
        setTimeout(() => {
            notification.className = 'notification';
        }, 3000);
    }

    // Verificar si hay una sesión activa y redirigir
    const currentUser = localStorage.getItem('currentUser') || sessionStorage.getItem('currentUser');
    if (currentUser) {
        window.location.href = 'MenuInicio.jsp'; 
    }

    const remember = localStorage.getItem('remember');
    if (remember === 'true') {
        document.getElementById('remember').checked = true;
        const savedUsername = localStorage.getItem('savedUsername');
        if (savedUsername) {
            document.getElementById('username').value = savedUsername;
        }
    }
});