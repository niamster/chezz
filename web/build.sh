#!/usr/bin/env bash

eslint src webpack.config.js || exit 1
npm run build