.PHONY: gen
gen: .clear
	# TODO: make go module
	cp -r ../gen/go gen
	go mod tidy

.PHONY: clear
clear:
	rm -rf gen

.PHONY: run
run:
	go run ./*.go
