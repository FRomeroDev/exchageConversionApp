document.addEventListener("DOMContentLoaded", function () {
  // CONVERSION MODULE -------------------------------------------------

  function convert() {
    // Get values from the form elements
    let amount = document.getElementById("amount").value;
    let currencyFrom = document.getElementById("currencyFrom").value;
    let currencyTo = document.getElementById("currencyTo").value;
    document.getElementById("result").innerHTML = "";

    // Create the data object to send
    let dataToSend = {
      amount: amount,
      currencyFrom: currencyFrom,
      currencyTo: currencyTo,
    };

    // Send a fetch request to the server for currency conversion
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
        // Display the result
        document.getElementById(
          "result"
        ).innerHTML = `${data.result} ${currencyTo}`;
        // Display conversion rates
        const conversionRateFromTo = data.conversionRateFromTo;
        const conversionRateToFrom = data.conversionRateToFrom;
        const conversionInfoElement = document.createElement("p");
        // Aplicar estilos CSS al elemento para hacerlo más pequeño y sin negritas
        conversionInfoElement.style.fontSize = "18px"; // Cambiar el tamaño de fuente a 12 píxeles
        conversionInfoElement.style.marginTop = "10px"; // Add top margin for spacing
        conversionInfoElement.style.fontWeight = "normal";
        conversionInfoElement.innerHTML = `1 ${currencyFrom} = ${conversionRateFromTo.toFixed(
          5
        )} ${currencyTo}<br>1 ${currencyTo} = ${conversionRateToFrom.toFixed(
          5
        )} ${currencyFrom}`;

        document.getElementById("result").appendChild(conversionInfoElement);
        console.log(data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  // Calculate button the make a conversion
  document
    .getElementById("formConversion")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      convert();
    });

  // Function to reset the form
  function resetForm() {
    document.getElementById("amount").value = "";
    document.getElementById("currencyFrom").value = "EUR";
    document.getElementById("currencyTo").value = "USD";
    document.getElementById("result").innerHTML = "";

    // Send a fetch request to reset the form on the server
    fetch("http://localhost:8080/excurrency/conversion?op=reset", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
    })
      .then(function (response) {
        if (response.status == 200) {
          console.log("Reset ok");
        } else {
          console.log("Error reset");
        }
      })
      .catch(function (error) {
        console.log("Error reset", error);
      });
  }
  document.getElementById("resetButton").addEventListener("click", resetForm);

  // LOGIN MODULE -------------------------------------------------
  // Get the current route
  const currentRoute = window.location.hash;
  // Function to display the username in the navigation bar
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

  // Promise function for user login
  function login() {
    return new Promise((resolve, reject) => {
      let login = document.getElementById("login");
      let password = document.getElementById("password");

      let user = {
        username: login.value,
        password: CryptoJS.SHA256(password.value).toString(),
      };
      // Send a fetch request to login into the form on the server
      fetch("http://localhost:8080//excurrency/conversion?op=login", {
        method: "POST",
        body: JSON.stringify(user),
        headers: {
          "Content-Type": "application/json",
        },
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
          resolve(); // Resolve the promise on success
          // Hide the login section and show the home section
          document.getElementById("welcome").style.display = "none";
          document.getElementById("home").style.display = "block";
          showUsername(data.username);
        })
        .catch(function (error) {
          console.log("KO no logueado", error);
          reject(); // Reject the promise on error
          alert("Incorrect user or password");
        });
    });
  }

  // Event to handle the login form submission
  document
    .getElementById("loginForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      login();
    });

  // Get a reference to the "Login" button in the menu bar
  const loginMenu = document.getElementById("loginMenu");
  // Add a click event to the "Login" button
  loginMenu.addEventListener("click", function (event) {
    event.preventDefault();
    // Call the login function and handle the resulting promise
    login()
      .then(() => {
        // Ocultar la sección de inicio de sesión y mostrar la de inicio
        document.getElementById("welcome").style.display = "none";
        document.getElementById("home").style.display = "block";
        showUsername(data.username);
      })
      .catch(function (error) {});
  });

  // WINDOWS CHANGE SPA HIDE / SHOW FUNCTIONALITY MODULE --------------------
  // Show the appropriate section based on the current route
  if (!currentRoute || currentRoute === "#/welcome") {
    // Set '#/welcome' as the default route
    document.getElementById("welcome").style.display = "block";
    document.getElementById("home").style.display = "none";
  } else if (currentRoute === "#/home") {
    document.getElementById("welcome").style.display = "none";
    document.getElementById("home").style.display = "block";
  }

  // LOG OUT MODULE ----------------------------------------------------
  // Function to log out the user
  function logout() {
    return new Promise((resolve, reject) => {
      fetch("http://localhost:8080/excurrency/conversion?op=logout", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
      })
        .then(function (response) {
          if (response.status == 200) {
            resolve();
            console.log("OK salida exitosa");
            document.getElementById("home").style.display = "none";
            document.getElementById("welcome").style.display = "block";
            hideUsername();
            resetForm();
          } else {
            reject();
            throw "KO";
          }
        })
        .catch(function (error) {
          console.log("Error al intentar cerrar sesión", error);
          reject();
        });
    });
  }
  // Get a reference to the "Logout" button in the menu bar
  const logoutButton = document.getElementById("logoutMenu");

  // Add a click event to the "Logout" button
  logoutButton.addEventListener("click", function (event) {
    event.preventDefault();
    // Call the logout function and handle the resulting promise
    logout();
  });
});
