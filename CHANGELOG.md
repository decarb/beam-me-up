# Changelog
All notable changes to this project will be documented in this file.

The format of this changelog file is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

[Unreleased]: https://github.com/decarb/beam-me-up/compare/master...develop
[1.2.1]: https://github.com/decarb/beam-me-up/releases/tag/v1.2.1
[1.2.0]: https://github.com/decarb/beam-me-up/releases/tag/v1.2.0

## [Unreleased]

## [1.2.1] - 12/07/2020
### Changed
- Changed the implementation of the `SubCommand` abstract class to make it more streamlined
- Allowed the `remove` and `list` sub-commands to be used from the console

## [1.2.0] - 12/07/2020
### Added
- Added a tab completer for the `beam` command and all the sub-commands belonging to `beam`
- Added an in memory repository for the locations to facilitate the tab-completer and reduce performance requirements