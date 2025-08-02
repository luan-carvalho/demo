document.addEventListener('DOMContentLoaded', function () {
    // === MODAL REFERENCES ===
    const addServiceModalEl = document.getElementById('addServiceModal');
    const serviceSearchModalEl = document.getElementById('serviceSearchModal');
    if (!addServiceModalEl) return;

    // === DOM ELEMENT REFERENCES ===
    const clientSelect = $('#clientSelect'); // Using jQuery selector for Select2
    const petSelect = document.getElementById('petSelect');
    const serviceGridBody = document.getElementById('serviceGridBody');
    const serviceGridFooter = document.getElementById('serviceGridFooter');
    const serviceTotalCell = document.getElementById('serviceTotal');
    const serviceSearchInput = document.getElementById('serviceSearchInput');
    const mainForm = document.getElementById('addServiceForm');

    // === INITIALIZE SELECT2 FOR CLIENT SEARCH ===
    clientSelect.select2({
        placeholder: 'Digite para buscar um cliente',
        // This is crucial for Select2 to work inside a Bootstrap modal
        dropdownParent: $('#addServiceModal')
    });

    // === EVENT LISTENERS ===

    // 1. Client selection change
    clientSelect.on('change', function () {
        const tutorId = $(this).val();
        if (tutorId) {
            fetchPetsForTutor(tutorId);
        }
    });

    // 2. Service selection from the service modal
    serviceSearchModalEl.addEventListener('click', function (e) {
        const serviceItem = e.target.closest('.service-select-item');
        if (!serviceItem) return;

        const { serviceId, serviceName, servicePrice } = serviceItem.dataset;
        addServiceToGrid(serviceId, serviceName, parseFloat(servicePrice));

        const modalInstance = bootstrap.Modal.getInstance(serviceSearchModalEl);
        modalInstance.hide();
    });

    // 3. Live search filter for services
    serviceSearchInput.addEventListener('input', function () {
        const searchTerm = this.value.toLowerCase();
        const allServices = serviceSearchModalEl.querySelectorAll('.service-select-item');
        allServices.forEach(item => {
            const serviceName = item.dataset.serviceName.toLowerCase();
            if (serviceName.includes(searchTerm)) {
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        });
    });

    // 4. Remove a service from the grid
    serviceGridBody.addEventListener('click', function (e) {
        const removeBtn = e.target.closest('.remove-service-btn');
        if (removeBtn) {
            removeBtn.closest('tr').remove();
            updateTotal();
        }
    });

    // 5. Add hidden inputs before submitting the form
    mainForm.addEventListener('submit', function (e) {
        // Clear any previous hidden inputs to avoid duplicates
        mainForm.querySelectorAll('input[name="petCareIds"]').forEach(input => input.remove());

        const serviceRows = serviceGridBody.querySelectorAll('tr');
        serviceRows.forEach(row => {
            const hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'petCareIds';
            hiddenInput.value = row.dataset.serviceId;
            mainForm.appendChild(hiddenInput);
        });
    });

    // === HELPER FUNCTIONS ===

    function fetchPetsForTutor(tutorId) {
        petSelect.disabled = true;
        petSelect.innerHTML = '<option>Carregando pets...</option>';
        fetch(`/api/tutors/${tutorId}/pet-options`)
            .then(response => response.text())
            .then(html => {
                petSelect.innerHTML = html;
                petSelect.disabled = false;
            })
            .catch(err => {
                console.error("Failed to fetch pets", err);
                petSelect.innerHTML = '<option>Erro ao carregar</option>';
            });
    }

    function addServiceToGrid(id, name, price) {
        if (serviceGridBody.querySelector(`tr[data-service-id="${id}"]`)) return;

        const newRow = document.createElement('tr');
        newRow.dataset.serviceId = id;
        newRow.dataset.servicePrice = price; // Store price for calculation

        const formattedPrice = `R$ ${price.toFixed(2).replace('.', ',')}`;

        newRow.innerHTML = `
            <td>${name}</td>
            <td class="text-end">${formattedPrice}</td>
            <td class="text-center">
                <button type="button" class="btn btn-sm btn-outline-danger remove-service-btn"><i class="bi bi-x-lg"></i></button>
            </td>
        `;
        serviceGridBody.appendChild(newRow);
        updateTotal();
    }

    function updateTotal() {
        let total = 0;
        const serviceRows = serviceGridBody.querySelectorAll('tr');

        if (serviceRows.length === 0) {
            serviceGridFooter.style.display = 'none';
            return;
        }

        serviceRows.forEach(row => {
            total += parseFloat(row.dataset.servicePrice);
        });

        serviceTotalCell.textContent = `R$ ${total.toFixed(2).replace('.', ',')}`;
        serviceGridFooter.style.display = 'table-footer-group'; // Show the footer
    }

    // === RESET MODAL WHEN CLOSED ===
    addServiceModalEl.addEventListener('hidden.bs.modal', function () {
        mainForm.reset();
        clientSelect.val(null).trigger('change'); // Reset Select2
        petSelect.innerHTML = '<option value="" disabled selected>Selecione um cliente</option>';
        petSelect.disabled = true;
        serviceGridBody.innerHTML = '';
        updateTotal(); // This will hide the footer
    });
});