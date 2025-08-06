$(document).ready(function () {

    const servicesChecklist = $('#servicesChecklist');
    const selectedServicesBody = $('#selectedServicesBody');
    const totalDisplay = $('#totalDisplay');
    const submitButton = $('#submitServiceButton');

    function validateForm() {
        // Use .val() to check for a value. It works for both new (ID) and edit (object.toString()) states.
        const areServicesSelected = servicesChecklist.find('input[type="checkbox"]:checked').length > 0;

        // Disable the button if either condition is false
        submitButton.prop('disabled', !(areServicesSelected));
    }

    servicesChecklist.on('change', function (e) {
        if ($(e.target).is('input[type="checkbox"]')) {
            updateSelectedServicesGrid();
            validateForm();
        }
    });

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