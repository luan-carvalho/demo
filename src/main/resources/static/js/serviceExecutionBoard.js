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
});