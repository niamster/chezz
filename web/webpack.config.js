const webpack = require('webpack');
const dotenv = require('dotenv');
const HtmlWebPackPlugin = require("html-webpack-plugin");
const fs = require('fs');
const path = require('path');
var glob = require("glob");

let htmlPagesDir = path.join(__dirname) + "/src/pages/";
let htmlPages = glob.sync("*.html", {cwd: htmlPagesDir}).map(function(entry) {
  return new HtmlWebPackPlugin({
    filename: entry,
    template: htmlPagesDir + entry,
  })
})

module.exports = (env) => {
  const envFileName = '.env.' + env.ENVIRONMENT;
  const envPath = path.join(__dirname) + '/' + envFileName;
  if (!fs.existsSync(envPath)) {
    throw new Error("Env file '" + envFileName + "' does not exist");
  }
  const envFile = dotenv.config({ path: envPath }).parsed;
  const envKeys = Object.keys(envFile).reduce((prev, next) => {
    prev[`process.env.${next}`] = JSON.stringify(envFile[next]);
    return prev;
  }, {});

  return {
    module: {
      rules: [
        {
          test: /\.(js|jsx)$/,
          exclude: /node_modules/,
          use: {
            loader: "babel-loader"
          }
        },
        {
          test: /\.html$/,
          use: [
            {
              loader: "html-loader"
            }
          ]
        },
        {
          test: /\.css$/,
          use: [
            {
              loader: "style-loader"
            },
            {
              loader: "css-loader"
            }
          ]
        }
      ]
    },
    devServer: {
      historyApiFallback: true,
    },
    plugins: [
      new webpack.DefinePlugin(envKeys),
    ].concat(htmlPages),
    resolve: {
      alias: {
        api: path.resolve(__dirname, "src", "api"),
        components: path.resolve(__dirname, "src", "components"),
        pages: path.resolve(__dirname, "src", "components", "pages"),
        css: path.resolve(__dirname, "src", "css"),
      }
    },
  }
};
