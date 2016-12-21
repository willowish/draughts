var path = require('path');
var pkg = require('../../package.json');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

var DEBUG = process.env.NODE_ENV === 'development';
var TEST = process.env.NODE_ENV === 'test';

var jsxLoader;
var sassLoader;
var cssLoader;
var fileLoader = 'file-loader?name=[path][name].[ext]';
var htmlLoader = [
  'html-loader',
].join('!');
var jsonLoader = ['json-loader'];

var sassParams = [
  'outputStyle=expanded',
  'includePaths[]=' + path.resolve(__dirname, '../scss'),
  'includePaths[]=' + path.resolve(__dirname, '../node_modules')
];

  jsxLoader = [];
if (DEBUG || TEST) {
  jsxLoader.push('babel-loader?optional=runtime');
  sassParams.push('sourceMap', 'sourceMapContents=true');
  sassLoader = [
    'style-loader',
    'css-loader?sourceMap',
    'postcss-loader',
    'sass-loader?' + sassParams.join('&')
  ].join('!');
  cssLoader = [
    'style-loader',
    'css-loader?sourceMap',
    'postcss-loader'
  ].join('!');

} else {
  jsxLoader.push('babel-loader?optional=runtime');
  sassLoader = ExtractTextPlugin.extract('style-loader', [
    'css-loader',
    'postcss-loader',
    'sass-loader?' + sassParams.join('&')
  ].join('!'));
  cssLoader = ExtractTextPlugin.extract('style-loader', [
    'css-loader',
    'postcss-loader'
  ].join('!'));
}

var exclude = /node_modules|public/;

var loaders = [
  {
    test: /\.js$/,
    exclude: exclude,
    loaders: jsxLoader
  },
  {
    test: /\.css$/,
    loader: cssLoader
  },
  { 
    test: /\.(woff|woff2|ttf|svg)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, 
    loader: "url-loader?limit=10000&minetype=application/font-woff" 
  },
  {
    test: /\.(jpe|jpg|gif|png|ico|woff|woff2|ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
    loader: fileLoader
  },
  {
    test: /\.json$/,
    exclude: /node_modules/,
    loaders: jsonLoader
  },
  {
    test: /\.html$/,
    exclude: exclude,
    loader: htmlLoader
  },
  {
    test: /\.scss$/,
    exclude: exclude,
    loader: sassLoader
  }
];

module.exports = loaders;
