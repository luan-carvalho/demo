// /static/js/app.js

function loadBreeds(specieId, selectedBreedId) {
    const breedSelect = $('#breed');
    if (!specieId) {
        breedSelect.html('<option value="" hidden>-- Selecione a espécie primeiro --</option>').trigger('change');
        return;
    }
    breedSelect.html('<option value="">Carregando...</option>').prop('disabled', true).trigger('change');

    fetch(`/breed/fetch?specieId=${specieId}`)
        .then(response => response.json())
        .then(breeds => {
            breedSelect.html('<option value="" hidden>-- Selecione a raça --</option>');
            breeds.forEach(breed => {
                const isSelected = selectedBreedId && selectedBreedId.toString() === breed.id.toString();
                const option = new Option(breed.description, breed.id, false, isSelected);
                breedSelect.append(option);
            });
            breedSelect.prop('disabled', false).trigger('change');
        })
        .catch(error => { console.error('Error loading breeds:', error); });
}

$(document).ready(function() {

    // --- LOGIC FOR THE PET FORM ---
    if ($('#petForm').length) {
        
        //
        // THE DEFINITIVE FIX: Use .each() to correctly scope `this`
        //
        $('.select2-searchable').each(function() {
            $(this).select2({
                theme: "bootstrap-5",
                width: '100%',
                // `this` now correctly refers to the individual <select> element
                dropdownParent: $(this).closest('.card-body') 
            });
        });

        const specieSelect = $('#specie');
        const initialSpecieId = specieSelect.val();

        if (initialSpecieId) {
            const breedToSelect = $('#breed').data('selected');
            loadBreeds(initialSpecieId, breedToSelect);
        }

        specieSelect.on('change', function() {
            loadBreeds($(this).val(), null);
        });
    }


    // --- LOGIC FOR THE TUTOR FORM ---
    if ($('#tutorForm').length) {
        if (document.getElementById('phone')) {
            new Cleave('#phone', {
                delimiters: ['(', ') ', '-'], blocks: [0, 2, 5, 4], numericOnly: true
            });
        }
    }

    // --- LOGIC FOR CLICKABLE TABLE ROWS ---
    if ($(".clickable-row").length) {
        $(".clickable-row").on("click", function(event) {
            if ($(event.target).closest('a.badge').length === 0) {
                window.location = $(this).data("href");
            }
        });
    }
});