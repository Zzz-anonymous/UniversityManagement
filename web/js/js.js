/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// side bar

        let arrow = document.querySelectorAll(".arrow");
        for (var i = 0; i < arrow.length; i++) {
            arrow[i].addEventListener("click", (e) => {
                let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
                arrowParent.classList.toggle("showMenu");
            });
        }
        let sidebar = document.querySelector(".sidebar");
        let sidebarBtn = document.querySelector(".bx-menu");
        console.log(sidebarBtn);
        sidebarBtn.addEventListener("click", () => {
            sidebar.classList.toggle("close");
        });



//-- loop programmes
// Define a function to initialize the programme options
    function initializeProgrammes() {
        // Define an array of programmes
        var programmes = [
            { id: "P1001", name: "RML" },
            { id: "P1002", name: "RBC" },
            { id: "P1003", name: "RPY" },
            { id: "P1004", name: "REU" },
            { id: "P1005", name: "RSD" },
            { id: "P1006", name: "RIT" },
            { id: "P1007", name: "RSP" },
            { id: "P1008", name: "RSE" },
            { id: "P1009", name: "RES" },
            { id: "P1010", name: "RBD" },
            { id: "P1011", name: "RAC" },
            { id: "P1012", name: "RFI" }
        ];

        // Get the select element
        var select = document.getElementById("programme");

        // Loop through the programmes array and add options to the select element
        programmes.forEach(function(programme) {
            var option = document.createElement("option");
            option.value = programme.id;
            option.text = programme.name;
            select.appendChild(option);
        });
    }

// Check all checkboxes
document.querySelectorAll('[data-check]').forEach(button => {
    button.addEventListener('click', e => {
        e.preventDefault();
        const name = e.target.dataset.check;
        // Set the checkbox as checked when the button is clicked
        document.querySelectorAll(`[name=${name}]`).forEach(checkbox => {
            checkbox.checked = true;
        });
    });
});


// Uncheck all checkboxes
document.querySelectorAll('[data-uncheck]').forEach(button => {
    button.addEventListener('click', e => {
        e.preventDefault();
        const name = e.target.dataset.uncheck;
        // Set the checkbox as unchecked when the button is clicked
        document.querySelectorAll(`[name=${name}]`).forEach(checkbox => {
            checkbox.checked = false;
        });
    });
});

// Row checkable, 按 table 的 row 可以 tick the checkbox
document.querySelectorAll('[data-checkable]').forEach(element => {
    element.addEventListener('click', e => {
        if (e.target.tagName.toLowerCase() === 'input' || e.target.tagName.toLowerCase() === 'a') return;
        
        const checkboxes = element.querySelectorAll(':scope input[type="checkbox"]');
        checkboxes.forEach(checkbox => {
            checkbox.checked = !checkbox.checked;
        });
    });
});
