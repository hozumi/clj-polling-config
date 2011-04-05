# clj-polling-config

Monitor file update.

## Usage

    (require 'hozumi.polling-config :as polling)

    (def config-ref (polling/run-background "conf.clj" 60000));;60 sec interval

    @config-ref
    ;=> {:a 1}
passing ref
    (def config-ref (ref nil))
    (polling/run-background "conf.clj" 60000 config-ref)
    @config-ref
    => {:a 1}
    
## Instalation
Leiningen
    [org.clojars.hozumi/clj-polling-config "1.0.4"]

## License

Copyright (C) 2010 Takahiro Hozumi

Distributed under the Eclipse Public License, the same as Clojure.
