/**
 * Webpack configuration for Mix, handles compilation
 * of JS and SCSS
 */

const mix = require('laravel-mix');

mix.js('resources/assets/js/app.js', 'public/js')
   .sass('resources/assets/sass/app.scss', 'public/css');