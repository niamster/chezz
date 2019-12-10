[![Build Status](https://travis-ci.org/niamster/chezz.svg?branch=master)](https://travis-ci.org/niamster/chezz)

**CHEZZ** is an online chess game.

# Status
Early development.

# Run local build
Run `rake run_dev` and go to `http://localhost:8000/` in your browser.

Each app can be built and launched independently, see `rake tasks` for the details.

# Production build
Run `rake build_prod` and find the output in `dist` directory.

Alternatively run `rake build_prod[/output/path]` to change the output directory.