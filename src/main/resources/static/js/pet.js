$(document).ready(function () {
    const specieSelect = $('#specie');
    const breedSelect = $('#breed');

    $('.select2-searchable').select2({
        theme: 'bootstrap-5'
    });

    function updateBreedDropdown(specieId, selectedBreedId) {
        breedSelect.prop('disabled', true).empty().append('<option value="">Carregando...</option>').trigger('change');

        if (!specieId) {
            breedSelect.empty().append('<option value="" selected disabled>Selecione uma espécie primeiro</option>').trigger('change');
            return;
        }

        const apiUrl = `/api/specie/${specieId}/breeds`;

        $.ajax({
            url: apiUrl,
            type: 'GET',
            success: function (breeds) {
                breedSelect.empty().append('<option value="" hidden>-- Selecione a raça --</option>');
                if (Array.isArray(breeds) && breeds.length > 0) {
                    breeds.forEach(breed => {
                        const isSelected = selectedBreedId && selectedBreedId.toString() === breed.id.toString();
                        const option = new Option(breed.description, breed.id, false, isSelected);
                        breedSelect.append(option);
                    });
                } else {
                    breedSelect.append('<option value="" disabled>Nenhuma raça encontrada</option>');
                }
                breedSelect.prop('disabled', false).trigger('change');
            },
            error: function () {
                console.error('Erro ao carregar raças.');
                breedSelect.empty().append('<option value="" disabled>Erro ao carregar raças</option>').trigger('change');
            }
        });
    }

    specieSelect.on('change', function () {
        updateBreedDropdown($(this).val(), null);
    });

    if (specieSelect.val()) {
        updateBreedDropdown(specieSelect.val(), breedSelect.data('selected'));
    }
});