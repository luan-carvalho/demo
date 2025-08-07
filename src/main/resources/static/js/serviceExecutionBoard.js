// This is the final, clean script.
document.addEventListener('DOMContentLoaded', function () {

    const deleteModalEl = document.getElementById('deleteConfirmationModal');
    if (deleteModalEl) {
        const serviceInfoText = document.getElementById('serviceInfoText');
        const deleteForm = document.getElementById('deleteForm');

        deleteModalEl.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const serviceId = button.getAttribute('data-service-id');
            const serviceInfo = button.getAttribute('data-service-info');

            serviceInfoText.textContent = serviceInfo;
            deleteForm.action = `/serviceExecution/${serviceId}/delete`;
        });


    }

    let currentDate = document.getElementById('dateISO').textContent

    flatpickr("#date-picker-container", {
        locale: "pt",
        dateFormat: "Y-m-d",
        defaultDate: currentDate,
        maxDate: "today",
        onChange: function (selectedDates, dateStr, instance) {
            window.location.href = `/serviceExecution?date=${dateStr}`;
        }
    });

    const paymentModalEl = document.getElementById('paymentModal');
    if (!paymentModalEl) return;

    // --- MODAL ELEMENTS ---
    const serviceIdSpan = document.getElementById('paymentServiceId');
    const totalAmountSpan = document.getElementById('paymentTotalAmount');
    const addedAmountSpan = document.getElementById('paymentAddedAmount'); // The new summary field
    const balanceSpan = document.getElementById('paymentBalance');
    const paymentsList = document.getElementById('paymentsList');
    const addPaymentToListForm = document.getElementById('addPaymentToListForm');
    const paymentAmountInput = document.getElementById('paymentAmount');
    const paymentTypeSelect = document.getElementById('paymentTypeSelect');
    const finalizeButton = document.getElementById('finalizePaymentButton');

    const addPaymentButton = addPaymentToListForm.querySelector('button[type="submit"]');

    let currentServiceId = null;
    let totalAmount = 0; // The total to be paid for this service
    let payments = []; // The temporary list of payments being built

    // --- UI UPDATE FUNCTION (The core logic) ---
    function updateUI() {
        paymentsList.innerHTML = '';
        let newTotal = 0;

        if (payments.length === 0) {
            paymentsList.innerHTML = '<tr><td colspan="3" class="text-center text-muted">Nenhum pagamento na lista.</td></tr>';
        } else {
            payments.forEach((p, index) => {
                const row = `
                    <tr data-index="${index}">
                        <td>${p.typeName}</td>
                        <td class="text-end">${formatCurrency(p.amount)}</td>
                        <td class="text-center">
                            <button type="button" class="btn btn-sm btn-outline-danger remove-new-payment-btn"><i class="bi bi-x-lg"></i></button>
                        </td>
                    </tr>`;
                paymentsList.insertAdjacentHTML('beforeend', row);
                newTotal += p.amount;
            });
        }

        const balance = totalAmount - newTotal;

        // Update all summary fields
        addedAmountSpan.textContent = formatCurrency(newTotal);
        balanceSpan.textContent = formatCurrency(balance);

        // Pre-fill the input with the remaining balance
        paymentAmountInput.value = balance > 0 ? balance.toFixed(2) : '0.00';

        if (balance <= 0.001) {
            // Disable the form fields and the "Add" button
            paymentAmountInput.disabled = true;
            paymentTypeSelect.disabled = true;
            addPaymentButton.disabled = true;
        } else {
            // Re-enable them if a payment is removed
            paymentAmountInput.disabled = false;
            paymentTypeSelect.disabled = false;
            addPaymentButton.disabled = false;
        }
        // --- END OF NEW LOGIC ---

        // Enable/disable finalize button
        const isBalanced = Math.abs(balance) < 0.001;
        finalizeButton.disabled = !isBalanced || payments.length === 0;

    }

    // --- EVENT LISTENERS ---

    // When the modal is about to be shown, grab data from the button and reset everything.
    paymentModalEl.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        currentServiceId = button.getAttribute('data-service-id');
        totalAmount = parseFloat(button.getAttribute('data-total-amount'));

        // Reset state for a new session
        payments = [];
        serviceIdSpan.textContent = currentServiceId;
        totalAmountSpan.textContent = formatCurrency(totalAmount);

        updateUI(); // Initial UI update
    });

    paymentAmountInput.addEventListener('input', function (e) {

        // console.log("preenchido " + parseFloat(paymentAmountInput.value))

        let amountInput = parseFloat(paymentAmountInput.value);
        let amountAdded = 0;

        payments.forEach(p => {
            amountAdded += p.amount;
        })

        let balance = totalAmount - amountAdded;


        if (amountInput > balance) {

            addPaymentButton.disabled = true;
            return;
            
        }
        
        addPaymentButton.disabled = false;


    })


    // When the "Adicionar à Lista" form is submitted
    addPaymentToListForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const amount = parseFloat(paymentAmountInput.value);
        const typeId = parseInt(paymentTypeSelect.value);
        const typeName = paymentTypeSelect.options[paymentTypeSelect.selectedIndex].text;

        if (amount > 0 && typeId) {
            payments.push({ amount, paymentTypeId: typeId, typeName });
            updateUI();
        }
    });

    // When a "remove" button is clicked
    paymentsList.addEventListener('click', function (e) {
        const removeBtn = e.target.closest('.remove-new-payment-btn');
        if (removeBtn) {
            const indexToRemove = parseInt(removeBtn.closest('tr').dataset.index);
            payments.splice(indexToRemove, 1);
            updateUI();
        }
    });

    // When the "Finalizar Pagamento" button is clicked
    finalizeButton.addEventListener('click', async function () {
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        const headers = { 'Content-Type': 'application/json' };
        headers[csrfHeader] = csrfToken;

        try {
            const response = await fetch(`/serviceExecution/${currentServiceId}/pay`, {
                method: 'POST',
                headers: headers,
                body: JSON.stringify({ payments: payments })
            });

            if (response.ok) {
                window.location.reload(); // Success! Reload the board page.
            } else { alert('Erro ao finalizar pagamento.'); }
        } catch (error) { console.error('Error:', error); alert('Erro de rede.'); }
    });

    // Helper to format currency
    function formatCurrency(value) {
        return `R$ ${Number(value || 0).toFixed(2).replace('.', ',')}`;
    }
});