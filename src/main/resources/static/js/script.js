$(document).ready(function() {

    if ($('#tutorForm').length) {
        if (document.getElementById('phone')) {
            new Cleave('#phone', {
                delimiters: ['(', ') ', '-'], blocks: [0, 2, 5, 4], numericOnly: true
            });
        }
    }

    if ($(".clickable-row").length) {
        $(".clickable-row").on("click", function(event) {
            if ($(event.target).closest('a.badge').length === 0) {
                window.location = $(this).data("href");
            }
        });
    }
});