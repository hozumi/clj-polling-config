# clj-polling-config

Monitor file update.

## Usage

    (require 'hozumi.polling-config :as polling)

    (future (polling/run "conf.yaml" 60000));;60 sec interval

    @polling/config
    ;=> {:a 1}

## Instalation
Leiningen
    [org.clojars.hozumi/clj-polling-config "1.0.0-SNAPSHOT"]

## License

Copyright (C) 2010 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
