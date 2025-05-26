/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/data')
        .then(response => response.text())
        .then(data => {
            document.getElementById('data').innerText = data;
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
});

