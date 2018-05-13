$(function() {
    $('.read-more').click(function() {
        const $longSummary  = $(this).siblings('.long-summary'),
              SLIDE_TIME    = 250;

        if ($longSummary.hasClass('expanded')) {
            $longSummary.slideUp(SLIDE_TIME);
            $(this).text('READ MORE');
        } else {
            $longSummary.slideDown(SLIDE_TIME);
            $(this).text('READ LESS');
        }

        $longSummary.toggleClass('expanded');
    });
});