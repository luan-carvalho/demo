document.addEventListener('DOMContentLoaded', function () {

    const getCsrfToken = () => document.querySelector('meta[name="_csrf_token"]')?.getAttribute('content');
    const getCsrfHeader = () => document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

    // =======================================================
    //          LOGIC FOR "ADD NEW SERVICE" MODAL
    // =======================================================
    const clientSelect = document.getElementById('clientSelect');
    const petSelect = document.getElementById('petSelect');
    const addServiceModal = document.getElementById('addServiceModal');

    if (clientSelect) {
        clientSelect.addEventListener('change', function () {
            const tutorId = this.value;

            // Disable pet select and show a loading message
            petSelect.disabled = true;
            petSelect.innerHTML = '<option value="">Carregando pets...</option>';

            if (!tutorId) {
                petSelect.innerHTML = '<option value="" disabled selected>Selecione um tutor primeiro</option>';
                return;
            }

            // Fetch the HTML options from our new API endpoint
            fetch(`/api/tutors/${tutorId}/pet-options`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text(); // Get the response as raw HTML text
                })
                .then(htmlOptions => {
                    // Inject the returned <option> tags into the select element
                    petSelect.innerHTML = htmlOptions;
                    petSelect.disabled = false; // Re-enable the dropdown
                })
                .catch(error => {
                    console.error('Error fetching pet options:', error);
                    petSelect.innerHTML = '<option value="" disabled>Erro ao carregar pets</option>';
                });
        });
    }

    if (addServiceModal) {
        addServiceModal.addEventListener('hidden.bs.modal', function () {
            document.getElementById('addServiceForm').reset();
            if (petSelect) {
                petSelect.innerHTML = '<option value="" selected disabled>Selecione um tutor primeiro</option>';
                petSelect.disabled = true;
            }
        });
    }

    // =======================================================
    //          DRAG-AND-DROP LOGIC (NO CHANGES NEEDED HERE)
    // =======================================================
    if (typeof Sortable !== 'undefined') {
        const kanbanColumns = document.querySelectorAll('.kanban-cards');
        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeader();

        kanbanColumns.forEach(column => {
            new Sortable(column, {
                group: 'kanban',
                animation: 150,
                ghostClass: 'sortable-ghost',
                onEnd: function (evt) {
                    const serviceId = evt.item.getAttribute('data-service-id');
                    const newStatus = evt.to.getAttribute('data-status');
                    const headers = { 'Content-Type': 'application/json' };
                    headers[csrfHeader] = csrfToken;

                    fetch(`/api/atendimentos/${serviceId}/status`, {
                        method: 'PATCH',
                        headers: headers,
                        body: JSON.stringify({ status: newStatus })
                    })
                        .then(response => {
                            if (!response.ok) { evt.from.appendChild(evt.item); alert('Erro ao atualizar o status.'); }
                        })
                        .catch(() => { evt.from.appendChild(evt.item); alert('Erro de rede.'); });
                }
            });
        });
    }
});