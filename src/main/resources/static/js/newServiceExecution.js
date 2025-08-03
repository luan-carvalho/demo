$(document).ready(function () {
    // === DOM REFERENCES ===
    const tutorIdInput = $('#tutorIdInput');
    const petIdInput = $('#petIdInput');
    const servicesChecklist = $('#servicesChecklist');
    const selectedServicesBody = $('#selectedServicesBody');
    const totalDisplay = $('#totalDisplay');
    const submitButton = $('#submitServiceButton');

    // === VALIDATION LOGIC ===
    function validateForm() {
        // Use .val() to check for a value. It works for both new (ID) and edit (object.toString()) states.
        const isClientSelected = tutorIdInput.val() && petIdInput.val();
        const areServicesSelected = servicesChecklist.find('input[type="checkbox"]:checked').length > 0;

        // Disable the button if either condition is false
        submitButton.prop('disabled', !(isClientSelected && areServicesSelected));
    }

    // === EVENT HANDLERS ===

    // Listen for clicks on any service checkbox
    servicesChecklist.on('change', function (e) {
        if ($(e.target).is('input[type="checkbox"]')) {
            updateSelectedServicesGrid();
            validateForm();
        }
    });

    // === LOGIC FOR 'NEW' MODE (MODAL) ===
    const clientPetSearchModalEl = document.getElementById('clientPetSearchModal');
    // This 'if' block ensures the modal logic only runs if the modal exists on the page (i.e., in 'new' mode)
    if (clientPetSearchModalEl) {
        const searchModal = new bootstrap.Modal(clientPetSearchModalEl);
        const searchInput = $('#searchClientPetInput');
        const clientPetList = $('#clientPetList');
        const selectedDisplay = $('#selectedClientPetDisplay');

        // Live search listener
        searchInput.on('input', function () {
            const searchTerm = $(this).val().toLowerCase();
            clientPetList.find('.client-pet-item').each(function () {
                const tutorName = $(this).data('tutor-name').toLowerCase();
                const petName = $(this).data('pet-name').toLowerCase();
                if (tutorName.includes(searchTerm) || petName.includes(searchTerm)) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        });

        // Item selection listener
        clientPetList.on('click', '.client-pet-item', function () {
            const tutorId = $(this).data('tutor-id');
            const tutorName = $(this).data('tutor-name');
            const petId = $(this).data('pet-id');
            const petName = $(this).data('pet-name');

            selectedDisplay.text(`${tutorName} / ${petName}`).removeClass('text-muted');
            tutorIdInput.val(tutorId);
            petIdInput.val(petId);

            searchModal.hide();
            validateForm(); // Re-validate after selection
        });

        // Reset search when modal closes
        $(clientPetSearchModalEl).on('hidden.bs.modal', function () {
            searchInput.val('');
            clientPetList.find('.client-pet-item').show();
        });
    }

    // === HELPER FUNCTION (for both modes) ===
    function updateSelectedServicesGrid() {
        selectedServicesBody.empty();
        let currentTotal = 0.0;
        const checkedServices = servicesChecklist.find('input[type="checkbox"]:checked');

        if (checkedServices.length === 0) {
            selectedServicesBody.html('<tr><td colspan="2" class="text-center text-muted py-3">Nenhum serviço selecionado.</td></tr>');
            totalDisplay.text('R$ 0,00');
        } else {
            checkedServices.each(function () {
                const price = parseFloat($(this).data('price'));
                const name = $(this).data('name');
                const row = `<tr><td>${name}</td><td class="text-end">R$ ${price.toFixed(2).replace('.', ',')}</td></tr>`;
                selectedServicesBody.append(row);
                currentTotal += price;
            });
            totalDisplay.text(`R$ ${currentTotal.toFixed(2).replace('.', ',')}`);
        }
    }

    updateSelectedServicesGrid();
    validateForm();
});