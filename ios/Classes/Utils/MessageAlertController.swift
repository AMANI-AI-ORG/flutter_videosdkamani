import Foundation
import UIKit

class MessageAlertController: UIViewController {

    private var message: String
    private var confirmAction: (() -> Void)?
    private var cancelAction: (() -> Void)?

    init(message: String, onConfirm: @escaping () -> Void, onCancel: (() -> Void)? = nil) {
        self.message = message
        self.confirmAction = onConfirm
        self.cancelAction = onCancel
        super.init(nibName: nil, bundle: nil)
        modalPresentationStyle = .overFullScreen
        modalTransitionStyle = .crossDissolve
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }

    private func setupUI() {
        view.backgroundColor = UIColor.black.withAlphaComponent(0.5)

        let alertView = UIView()
        alertView.backgroundColor = .white
        alertView.layer.cornerRadius = 12

        let messageLabel = UILabel()
        messageLabel.text = message
        messageLabel.textAlignment = .center
        messageLabel.numberOfLines = 0
        messageLabel.textColor = UIColor.black

        let confirmButton = UIButton(type: .system)
        confirmButton.setTitle("Confirm", for: .normal)
        confirmButton.addTarget(self, action: #selector(confirmTapped), for: .touchUpInside)

        let cancelButton = UIButton(type: .system)
        cancelButton.setTitle("Cancel", for: .normal)
        cancelButton.addTarget(self, action: #selector(cancelTapped), for: .touchUpInside)

        view.addSubview(alertView)
        alertView.translatesAutoresizingMaskIntoConstraints = false

        alertView.addSubview(messageLabel)
        alertView.addSubview(confirmButton)
        alertView.addSubview(cancelButton)

        messageLabel.translatesAutoresizingMaskIntoConstraints = false
        confirmButton.translatesAutoresizingMaskIntoConstraints = false
        cancelButton.translatesAutoresizingMaskIntoConstraints = false

        // Constraints
        NSLayoutConstraint.activate([
            alertView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            alertView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            alertView.widthAnchor.constraint(equalToConstant: 300),
            
            messageLabel.topAnchor.constraint(equalTo: alertView.topAnchor, constant: 20),
            messageLabel.leadingAnchor.constraint(equalTo: alertView.leadingAnchor, constant: 16),
            messageLabel.trailingAnchor.constraint(equalTo: alertView.trailingAnchor, constant: -16),

            confirmButton.topAnchor.constraint(equalTo: messageLabel.bottomAnchor, constant: 20),
            confirmButton.leadingAnchor.constraint(equalTo: alertView.leadingAnchor, constant: 16),
            confirmButton.trailingAnchor.constraint(equalTo: alertView.trailingAnchor, constant: -16),

            cancelButton.topAnchor.constraint(equalTo: confirmButton.bottomAnchor, constant: 10),
            cancelButton.leadingAnchor.constraint(equalTo: alertView.leadingAnchor, constant: 16),
            cancelButton.trailingAnchor.constraint(equalTo: alertView.trailingAnchor, constant: -16),
            cancelButton.bottomAnchor.constraint(equalTo: alertView.bottomAnchor, constant: -20)
        ])
    }

    @objc private func confirmTapped() {
        dismiss(animated: true) {
            self.confirmAction?()
        }
    }

    @objc private func cancelTapped() {
        dismiss(animated: true) {
            self.cancelAction?()
        }
    }
}