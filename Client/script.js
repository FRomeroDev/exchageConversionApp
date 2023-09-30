document.addEventListener("DOMContentLoaded", function () {
  // Función para crear el boleto de lotería
  function createLotteryTicket() {
    // Obtener el elemento del boleto en el DOM
    var lotteryTicket = document.getElementById("lotteryTicket");
    // Contador para llevar un registro de los números seleccionados
    var selectedCount = 0;
    // Crear una tabla para mostrar los números
    var table = document.createElement("table");
    table.classList.add("table");
    // Arreglo para guardar los números seleccionados
    var selectedNumbers = [];

    // Crear filas y casilleros para los números
    var number = 1;
    for (var i = 0; i < 10; i++) {
      // 7 filas (7 x 5 = 35 números)
      var row = document.createElement("tr");
      for (var j = 0; j < 5; j++) {
        // Verificar si hemos alcanzado el número 50 o se han seleccionado ya 6 números
        if (number <= 49 && selectedCount < 6) {
          // 5 casilleros por fila
          var cell = document.createElement("td");
          var numberButton = document.createElement("button");

          // Configurar el número y el evento de selección
          numberButton.textContent = number;
          numberButton.classList.add("btn", "btn-light");
          numberButton.addEventListener("click", function () {
            // Cambiar el fondo del casillero al seleccionar/deseleccionar
            this.classList.toggle("btn-light");
            this.classList.toggle("btn-success");

            var numberValue = parseInt(this.textContent);

            // Verificar si el número ya está en el arreglo
            var index = selectedNumbers.indexOf(numberValue);

            if (index === -1) {
              // Agregar el número al arreglo si aún no está en él y no se han seleccionado 6 números
              if (selectedNumbers.length < 6) {
                selectedNumbers.push(numberValue);
                this.classList.remove("btn-light"); // Quitar el color gris
                this.classList.add("btn-success"); // Agregar el color verde
              }
            } else if (index !== -1) {
              // Eliminar el número del arreglo si ya está en él y permitir seleccionar otro en su lugar
              selectedNumbers.splice(index, 1);
              this.classList.remove("btn-success"); // Quitar el color verde
              this.classList.add("btn-light"); // Agregar el color gris
            }
          });

          // Agregar el botón al casillero
          cell.appendChild(numberButton);
          row.appendChild(cell);
        }
        // Incrementar el número
        number++;
      }
      // Agregar la fila a la tabla
      table.appendChild(row);
    }
    // Agregar la tabla al boleto
    lotteryTicket.appendChild(table);
  }

  // Llamar a la función para crear el boleto cuando se cargue la página
  window.addEventListener("load", createLotteryTicket);
});
