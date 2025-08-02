document.addEventListener('DOMContentLoaded', function () {
    // === DOM ELEMENT REFERENCES ===
    const clientSelect = $('#clientSelect'); // Use jQuery for Select2
    const petSelect = document.getElementById('petSelect');
    const servicesChecklist = document.getElementById('servicesChecklist');
    const selectedServicesBody = document.getElementById('selectedServicesBody');
    const totalDisplay = document.getElementById('totalDisplay');

    // === INITIALIZE SELECT2 FOR CLIENT SEARCH ===
    clientSelect.select2({
        placeholder: 'Digite para buscar um cliente',
        // Optional: If you use a layout with a fixed header, you might need dropdownParent
        // dropdownParent: clientSelect.parent() 
    });

    // === EVENT LISTENERS ===

    // 1. When a client is selected
    clientSelect.on('change', function () {
        const tutorId = $(this).val();
        updatePetDropdown(tutorId);
    });

    // 2. When a service checkbox is checked or unchecked
    servicesChecklist.addEventListener('change', function (e) {
        if (e.target.matches('input[type="checkbox"]')) {
            updateSelectedServicesGrid();
        }
    });

    // === HELPER FUNCTIONS ===

    function updatePetDropdown(tutorId) {
        petSelect.disabled = true;
        petSelect.innerHTML = '<option value="">Carregando...</option>';

        if (!tutorId) {
            petSelect.innerHTML = '<option value="" selected disabled>Selecione um cliente primeiro</option>';
            return;
        }

        fetch(`/tutor/${tutorId}/pets`)
            .then(response => response.text())
            .then(html => {
                petSelect.innerHTML = html;
                petSelect.disabled = false;
            })
            .catch(error => {
                console.error('Error fetching pets:', error);
                petSelect.innerHTML = '<option value="" disabled>Erro ao carregar pets</option>';
            });
    }

    function updateSelectedServicesGrid() {
        // Clear the current grid
        selectedServicesBody.innerHTML = '';
        let currentTotal = 0.0;

        const checkedServices = servicesChecklist.querySelectorAll('input[type="checkbox"]:checked');

        if (checkedServices.length === 0) {
            selectedServicesBody.innerHTML = '<tr><td colspan="2" class="text-center text-muted">Nenhum serviço selecionado.</td></tr>';
            totalDisplay.textContent = 'R$ 0,00';
            return;
        }

        checkedServices.forEach(checkbox => {
            const price = parseFloat(checkbox.dataset.price);
            const name = checkbox.dataset.name;

            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                <td>${name}</td>
                <td class="text-end">R$ ${price.toFixed(2).replace('.', ',')}</td>
            `;
            selectedServicesBody.appendChild(newRow);

            currentTotal += price;
        });

        totalDisplay.textContent = `R$ ${currentTotal.toFixed(2).replace('.', ',')}`;
    }

    // Initial call to set the grid state correctly on page load
    updateSelectedServicesGrid();
});