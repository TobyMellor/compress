/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(1);
module.exports = __webpack_require__(2);


/***/ }),
/* 1 */
/***/ (function(module, exports) {

$(function () {
    $.fn.reverse = [].reverse;

    var SLIDE_TIME = 250;

    $('.read-more').click(function () {
        var $longSummary = $(this).siblings('.long-summary');

        if ($longSummary.hasClass('expanded')) {
            $longSummary.slideUp(SLIDE_TIME);
            $(this).text('READ MORE');
        } else {
            $longSummary.slideDown(SLIDE_TIME);
            $(this).text('READ LESS');
        }

        $longSummary.toggleClass('expanded');
    });

    $('.navbar .main-navbar').click(function () {
        var $ul = $(this).siblings('ul'),
            $navbar = $(this).parent(),
            $chevron = $(this).find('i');

        if ($navbar.hasClass('expanded')) {
            $ul.slideUp(SLIDE_TIME);
        } else {
            $ul.slideDown(SLIDE_TIME);
        }

        $navbar.toggleClass('expanded');
    });

    $('.navbar ul li').click(function () {
        var $textSection = $('.text-section[data-slug="' + $(this).data('slug') + '"]');

        $('html, body').animate({
            scrollTop: $textSection.offset().top
        });
    });

    $(window).scroll(function () {
        var scrollTop = $(this).scrollTop() + $('.main-navbar').outerHeight(),
            $textSections = $('.text-section').reverse(),
            $mainNavbar = $('.main-navbar'),
            $mainNavbarText = $mainNavbar.find('span'),
            $mainNavbarUl = $mainNavbar.siblings('ul');

        $textSections.each(function () {
            var $currentTextSection = $(this);

            if ($currentTextSection.offset().top <= scrollTop) {
                $mainNavbarText.text($currentTextSection.find('.details h1').text());
                $mainNavbarUl.find('li').removeClass('active');
                $mainNavbarUl.find('li[data-slug="' + $currentTextSection.data('slug') + '"]').addClass('active');
                return false;
            }
        });
    });

    $('.image-wrapper').each(function () {
        var $imageWrapper = $(this),
            $image = $imageWrapper.find('img');

        var $fakeImage = $('<img>').attr('src', $image.data('src')).on('load', function () {
            if (this.complete && this.naturalWidth) {
                $imageWrapper.addClass('loaded').css('height', '');
                $image.replaceWith($fakeImage);
            }
        });
    });
});

/***/ }),
/* 2 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ })
/******/ ]);