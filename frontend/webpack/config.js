var path = require('path');
var util = require('util');
var autoprefixer = require('autoprefixer-core');
var pkg = require('../../package.json');

var loaders = require('./loaders');
var plugins = require('./plugins');

var DEBUG = process.env.NODE_ENV === 'development';
var TEST = process.env.NODE_ENV === 'test';
var MINI = process.env.NODE_ENV === 'mini';

var jsBundle = util.format('[name].%s.js', pkg.version);

var entry = {
  index: ['./app/indexApp.js']
};

var config = {
  context: path.join(__dirname, '../src'),
  cache: DEBUG,
  debug: DEBUG,
  target: 'web',
  devtool: !MINI ? 'inline-source-map' : false,
  entry: entry,
  output: {
    path: path.resolve(pkg.config.buildDir),
    publicPath: '/assets/app/',
    filename: jsBundle,
    pathinfo: false
  },
  module: {
    loaders: loaders
  },
  postcss: [
    autoprefixer
  ],
  plugins: plugins,
  resolve: {
    extensions: ['', '.js', '.json']
  }
};

module.exports = config;
