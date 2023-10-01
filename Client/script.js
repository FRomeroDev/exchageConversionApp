document.addEventListener("DOMContentLoaded", function () {
  // CALCULAR - CONVERSION -------------------------------------------------
  function convert() {
    // Obtener los valores de los elementos del formulario
    let amount = document.getElementById("amount").value;
    let currencyFrom = document.getElementById("currencyFrom").value;
    let currencyTo = document.getElementById("currencyTo").value;

    // Limpiar el contenido del resultado anterior
    document.getElementById("result").innerHTML = "";

    // Crear el objeto de datos a enviar
    let dataToSend = {
      amount: amount,
      currencyFrom: currencyFrom,
      currencyTo: currencyTo,
    };

    fetch("http://localhost:8080/excurrency/conversion?op=conversion", {
      method: "POST",
      body: JSON.stringify(dataToSend),
      headers: {
        "Content-Type": "application/json",
      },
      // with credentials
      credentials: "include",
    })
      .then(function (response) {
        return response.json();
      })
      .then(function (data) {
        // Mostrar el resultado
        document.getElementById("result").innerHTML = data.result;
        console.log(data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  // Cuando el usuario presione el botón de calcular
  // se ejecuta la función convert
  document
    .getElementById("formConversion")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      convert();
    });

  function resetForm() {
    // Aquí puedes escribir el código para restablecer los campos del formulario
    // Por ejemplo, puedes establecer los valores de los campos en blanco o en su valor predeterminado.
    document.getElementById("amount").value = "";
    document.getElementById("currencyFrom").value = "EUR"; // Establecer a "Euro"
    document.getElementById("currencyTo").value = "USD"; // Establecer a "Dólar estadounidense"
    document.getElementById("result").innerHTML = "";

    // Realizar una solicitud fetch para restablecer el formulario en el servidor
    fetch("http://localhost:8080/excurrency/conversion?op=reset", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      // with credentials
      credentials: "include",
    })
      .then(function (response) {
        if (response.status == 200) {
          console.log("Formulario restablecido con éxito en el servidor");
        } else {
          console.log("Error al restablecer el formulario en el servidor");
        }
      })
      .catch(function (error) {
        console.log("Error al restablecer el formulario", error);
      });
  }
  // Llamar a la función resetForm() cuando se hace clic en el botón "Reset"
  document.getElementById("resetButton").addEventListener("click", resetForm);

  // LOGIN -------------------------------------------------
  // Función para mostrar el nombre en la barra de navegación
  function showUsername(username) {
    const usernameElement = document.getElementById("username");
    if (usernameElement) {
      usernameElement.textContent = username;
    }
  }

  // Función para ocultar el nombre en la barra de navegación
  function hideUsername() {
    const usernameElement = document.getElementById("username");
    if (usernameElement) {
      usernameElement.textContent = "";
    }
  }

  function login() {
    return new Promise((resolve, reject) => {
      let login = document.getElementById("login");
      let password = document.getElementById("password");

      let user = {
        username: login.value,
        //"password": password.value
        password: CryptoJS.SHA256(password.value).toString(),
      };

      fetch("http://localhost:8080//excurrency/conversion?op=login", {
        method: "POST",
        body: JSON.stringify(user),
        headers: {
          "Content-Type": "application/json",
        },
        // with credentials
        credentials: "include",
      })
        .then(function (response) {
          if (response.status == 200) {
            return response.json();
          } else {
            throw "KO";
          }
        })
        .then(function (data) {
          console.log("OK logueado", data);
          resolve(); // Resuelve la promesa en caso de éxito
          // Ocultar la sección de inicio de sesión y mostrar la de inicio
          document.getElementById("welcome").style.display = "none";
          document.getElementById("home").style.display = "block";
          showUsername(data.username);
        })
        .catch(function (error) {
          console.log("KO no logueado", error);
          reject(); // Rechaza la promesa en caso de error
          alert("Incorrect user or password");
        });
    });
  }

  // Obtener la ruta actual
  const currentRoute = window.location.hash;
  // Evento para manejar el envío del formulario de inicio de sesión
  document
    .getElementById("loginForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      login();
    });

  // Obtén una referencia al botón "Login" en la barra de menú
  const loginMenu = document.getElementById("loginMenu");

  // Asigna un evento de clic al botón "Login"
  loginMenu.addEventListener("click", function (event) {
    event.preventDefault();
    // Llama a la función login y maneja la promesa resultante
    login()
      .then(() => {
        // Ocultar la sección de inicio de sesión y mostrar la de inicio
        document.getElementById("welcome").style.display = "none";
        document.getElementById("home").style.display = "block";
        showUsername(data.username);
      })
      .catch(function (error) {});
  });

  // Mostrar la sección correspondiente a la ruta actual
  if (!currentRoute || currentRoute === "#/welcome") {
    // Establecer '#/welcome' como la ruta por defecto
    document.getElementById("welcome").style.display = "block";
    document.getElementById("home").style.display = "none";
  } else if (currentRoute === "#/home") {
    // Ocultar la sección de inicio de sesión y mostrar la de inicio
    document.getElementById("welcome").style.display = "none";
    document.getElementById("home").style.display = "block";
  }

  // LOG OUT ----------------------------------------------------
  function logout() {
    return new Promise((resolve, reject) => {
      fetch("http://localhost:8080/excurrency/conversion?op=logout", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        // with credentials
        credentials: "include",
      })
        .then(function (response) {
          if (response.status == 200) {
            resolve(); // Resuelve la promesa en caso de éxito
            console.log("OK salida exitosa");
            document.getElementById("home").style.display = "none";
            document.getElementById("welcome").style.display = "block";
            hideUsername();
            resetForm();
          } else {
            reject(); // Rechaza la promesa en caso de error
            throw "KO";
          }
        })
        .catch(function (error) {
          console.log("Error al intentar cerrar sesión", error);
          reject(); // Rechaza la promesa en caso de error
        });
    });
  }

  const logoutButton = document.getElementById("logoutMenu");

  // Asigna un evento de clic al botón "Logout"
  logoutButton.addEventListener("click", function (event) {
    event.preventDefault();
    // Llama a la función logout y maneja la promesa resultante
    logout();
  });
});
