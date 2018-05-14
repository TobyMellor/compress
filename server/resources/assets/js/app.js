$(function() {
    $.fn.reverse = [].reverse;

    const SLIDE_TIME = 250;

    $('.read-more').click(function() {
        const $longSummary = $(this).siblings('.long-summary');

        if ($longSummary.hasClass('expanded')) {
            $longSummary.slideUp(SLIDE_TIME);
            $(this).text('READ MORE');
        } else {
            $longSummary.slideDown(SLIDE_TIME);
            $(this).text('READ LESS');
        }

        $longSummary.toggleClass('expanded');
    });

    $('.main-navbar').click(function() {
        const $ul      = $(this).siblings('ul'),
              $navbar  = $(this).parent(),
              $chevron = $(this).find('i');

        if ($navbar.hasClass('expanded')) {
            $ul.slideUp(SLIDE_TIME);
        } else {
            $ul.slideDown(SLIDE_TIME);
        }

        $navbar.toggleClass('expanded');
    });

    $(window).scroll(function() {
        const scrollTop       = $(this).scrollTop() + $('.main-navbar').outerHeight(),
              $textSections   = $('.text-section').reverse(),
              $mainNavbar     = $('.main-navbar'),
              $mainNavbarText = $mainNavbar.find('span'),
              $mainNavbarUl   = $mainNavbar.siblings('ul');

        $textSections.each(function() {
            const $currentTextSection = $(this);

            if ($currentTextSection.offset().top <= scrollTop) {
                $mainNavbarText.text($currentTextSection.find('.details h1').text());
                $mainNavbarUl.find('li').removeClass('active');
                $mainNavbarUl.find(`li[data-slug="${$currentTextSection.data('slug')}"]`).addClass('active');
                return false;
            }
        });
    });

    $('.image-wrapper').each(function() {
        const $imageWrapper = $(this),
                $image        = $imageWrapper.find('img');

        const $fakeImage =
            $('<img>').attr('src', $image.data('src'))
                .on('load', function() {
                    if (this.complete && this.naturalWidth) {
                        $imageWrapper.addClass('loaded');
                        $image.replaceWith($fakeImage);
                    }
                });
    });
});