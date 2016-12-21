var path = require('path');
var util = require('util');
var BowerWebpackPlugin = require('bower-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var webpack = require('webpack');
var pkg = require('../../package.json');

var DEBUG = process.env.NODE_ENV === 'development';
var TEST = process.env.NODE_ENV === 'test';

var cssBundle = path.join('css', util.format('[name].%s.css', pkg.version));

var plugins = [
  new webpack.optimize.OccurenceOrderPlugin(),
];
if (!TEST) {
  plugins.push(
    new ExtractTextPlugin(cssBundle, {
      allChunks: true
    })
  );
}
if (!DEBUG && !TEST) {
  plugins.push(
    new webpack.optimize.UglifyJsPlugin()
  );
}
if (!TEST) {
  plugins.push(
    new webpack.optimize.DedupePlugin(),
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: JSON.stringify('production')
      }
    }),
    new webpack.NoErrorsPlugin()
  );
}
module.exports = plugins;